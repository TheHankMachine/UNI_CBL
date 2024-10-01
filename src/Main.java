import javax.swing.*;
import java.awt.event.ActionEvent;

import game.Config;
import render.Renderer;
import input.Input;

public class Main {
    public static void main(String[] args) {

        new Renderer();
        new Input();

        Timer timer = new Timer(Config.TARGET_TICK_MS, Main::update);
        timer.start();



        new Sprite();

    }

    public static void update(ActionEvent e){
        Renderer.redraw();
    }
}