import javax.swing.*;
import java.awt.event.ActionEvent;

import render.Renderer;
import input.Input;

public class Main {
    public static void main(String[] args) {

        new Renderer();

        Timer timer = new Timer(Config.TARGET_TICK_MS, Main::update);
        timer.start();

//        InputMap.initialise();
        new Input();

//        for(int i = 0; i < 20; i++){
        new Sprite();
//        }

    }

    public static void update(ActionEvent e){
        Renderer.redraw();
    }
}