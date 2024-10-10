package game;

import engine.Game;
import engine.GameConfig;
import engine.example.Button;

import java.awt.*;

public class PlaneArcade extends Game {

    public PlaneArcade(){
        super(new GameConfig(320, 240, Color.WHITE, "src/assets/", 50));

//        for(int i = 0; i < 100; i++) new DVD();
        new Button();

//        new CursorFollower();
//        new BoundryBreaker();

        register();
    }

    @Override
    public void update() {
    }
}
