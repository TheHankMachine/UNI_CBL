package engine;

import engine.example.DefaultFont;
import engine.input.Input;
import engine.render.display.Display;
import engine.render.SpriteFont;
import engine.update.Updateable;
import jdk.jshell.SourceCodeAnalysis;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public abstract class Game {

    public static SpriteFont debug_displayText;
    public transient int debug_updateTimeMs;
    public transient int debug_renderTimeMs;

    private static Game instance = null;
    public static Game getInstance(){
        if(instance == null) {
            throw new RuntimeException("Game instance not found");
        }
        return instance;
    }

    protected final GameConfig config;

    private final Display display;
    private final Input input;

    public Game(GameConfig config){
        instance = this;

        this.config = config;

        display = new Display(config);
        input = new Input();

        debug_displayText = new DefaultFont("test", 0, 0){
            @Override
            public DepthLayer getDepth() {
                return DepthLayer.UI;
            }
        };
    }

    public final void register(){
        Timer timer = new Timer(config.targetTickMs,
            this::periodic
        );
        timer.start();
    }

    public final void periodic(ActionEvent e){
        long startTime = System.nanoTime();

//        update();
        var a = CompletableFuture.runAsync(() -> {
            update();
            Updateable.updateAll();
        });

//        long startTime = System.nanoTime();


        var b = CompletableFuture.runAsync(() -> {
            display.repaint();
        });

//        b.join();

        while(!a.isDone() || !b.isDone());

        int duration = (int) (System.nanoTime() - startTime) / 1_000_000;

        debug_displayText.setText(String.format("Uptime: %02d'/,",
                duration * 100 / config.targetTickMs
        ));

    }

    public float getDefaultScale(){
        return 1f;
    }

    public final Input getInput(){
        return input;
    }

    public abstract void update();

    public final Display getDisplay(){
        return display;
    }

    /**
     * @return the width of the game config
     */
    public int getDisplayWidth(){
        return config.width;
    }

    /**
     * @return the height of the game config
     */
    public int getDisplayHeight(){
        return config.height;
    }

    public BufferedImage loadImage(String fileName){
        File file = new File(config.assetFilePath + fileName);

        try{
            return ImageIO.read(file);
        }catch(IOException e){
            throw new RuntimeException(String.format("File not found:\n%s", file.getAbsolutePath()));
        }
    }
}
