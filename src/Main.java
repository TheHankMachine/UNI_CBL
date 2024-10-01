import javax.swing.*;
import java.awt.event.ActionEvent;

import game.Config;
import game.Sprite;
import game.SpriteSheet;
import render.Renderer;
import input.Input;

public class Main {
    public static void main(String[] args) {

        new Renderer();
        new Input();

        Timer timer = new Timer(Config.TARGET_TICK_MS, Main::update);
        timer.start();

        var a = new SpriteSheet("player.png", 16, 16);

//        new Sprite("dvd.png");
    }

    public static void update(ActionEvent e){
        Renderer.redraw();
    }
}