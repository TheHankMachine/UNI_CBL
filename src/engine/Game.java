package engine;

import engine.defaults.DefaultFont;
import engine.input.Input;
import engine.render.Renderer;
import engine.render.SpriteFont;
import engine.update.Updateable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Game {

    public static SpriteFont debug_displayText;
    public static int debug_updateTimeMs;
    public static int debug_renderTimeMs;

    private static Game instance = null;
    public static Game getInstance(){
        if(instance == null) {
            throw new RuntimeException("Game instance not found");
        }
        return instance;
    }

    protected final GameConfig config;

    private final Renderer renderer;
    private final Input input;

    public Game(GameConfig config){
        instance = this;

        this.config = config;

        renderer = new Renderer(config);
        input = new Input();

        debug_displayText = new DefaultFont("test", 0, 0){
            @Override
            public DepthLayer getDepth() {
                return DepthLayer.FOREGROUND;
            }
        };
    }

    public final void register(){
        Timer timer = new Timer(config.targetTickMs, this::periodic);
        timer.start();
    }

    public final void periodic(ActionEvent e){
        long startTime = System.nanoTime();

        update();

        Updateable.updateAll();

        debug_updateTimeMs = (int) (System.nanoTime() - startTime) / 1_000_000;

        renderer.repaint();

        debug_displayText.setText(String.format("Update time: %dms\nRender time: %dms\nUptime: %d\'/.",
            debug_updateTimeMs, debug_renderTimeMs,
            (debug_updateTimeMs + debug_renderTimeMs) * 100 / config.targetTickMs
        ));
    }

    public abstract void update();

    public final Renderer getRenderer(){
        return renderer;
    }

    public final Input getInput(){
        return input;
    }

    public int getWidth(){
        return config.width;
    }

    public int getHeight(){
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
