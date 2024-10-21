package engine;

import engine.input.InputHandler;
import engine.render.display.Display;
import engine.update.Updateable;

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

    private final Display display;
    private final InputHandler inputHandler;

    public Game(GameConfig config){
        instance = this;

        this.config = config;

        display = new Display(config);
        inputHandler = new InputHandler();
    }

    public final void register(){
        Timer timer = new Timer(config.targetTickMs,
            this::periodic
        );
        timer.start();
    }

    public final void periodic(ActionEvent e){
        update();
        Updateable.updateAll();

        display.repaint();
    }

    public float getDefaultScale(){
        return 1f;
    }

    public final InputHandler getInput(){
        return inputHandler;
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
