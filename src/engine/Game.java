package engine;

import engine.input.Input;
import engine.render.Renderer;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class Game {

    private static Game instance = null;
    public static Game getInstance(){
        if(instance == null) {
            throw new RuntimeException("Game instance not found");
        }
        return instance;
    }

    private final GameConfig config;

    private final Renderer renderer;
    private final Input input;

    public Game(GameConfig config){
        instance = this;

        this.config = config;

        renderer = new Renderer(config);
        input = new Input();

        Timer timer = new Timer(config.targetTickMs, this::periodic);
        timer.start();
    }

    public final void periodic(ActionEvent e){
        renderer.repaint();
    }

    public final Renderer getRenderer(){
        return renderer;
    }

    public final Input getInput(){
        return input;
    }
}
