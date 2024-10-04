package engine;

import engine.Util.DefaultFont;
import engine.input.Input;
import engine.render.Renderer;
import engine.render.SpriteFont;

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

        debug_displayText = new DefaultFont("test", 0, 0);
    }

    public final void register(){
        Timer timer = new Timer(config.targetTickMs, this::periodic);
        timer.start();
    }

    public final void periodic(ActionEvent e){
        long startTime = System.nanoTime();

        update();

        debug_updateTimeMs = (int) (System.nanoTime() - startTime) / 1_000_000;

        renderer.repaint();

        debug_displayText.setText(String.format("update time: %dms\nrender time: %dms",
            Game.debug_updateTimeMs, Game.debug_renderTimeMs
        ));
    }

    public abstract void update();

    public final Renderer getRenderer(){
        return renderer;
    }

    public final Input getInput(){
        return input;
    }

    public BufferedImage loadImage(String assetName){
        File file = new File(config.assetFilePath + assetName);

        try{
            return ImageIO.read(file);
        }catch(IOException e){
            throw new RuntimeException(String.format("File not found:\n%s", file.getAbsolutePath()));
        }
    }
}
