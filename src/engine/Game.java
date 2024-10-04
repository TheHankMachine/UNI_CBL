package engine;

import engine.input.Input;
import engine.render.Renderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Game {

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
    }

    public final void register(){
        Timer timer = new Timer(config.targetTickMs, this::periodic);
        timer.start();
    }

    public final void periodic(ActionEvent e){
        update();
        renderer.repaint();
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
