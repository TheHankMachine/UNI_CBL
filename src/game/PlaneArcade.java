package game;

import engine.Game;
import engine.GameConfig;
import engine.examples.BoundryBreaker;
import engine.examples.CursorFollower;
import engine.examples.DVD;

import java.awt.*;

public class PlaneArcade extends Game {

    public PlaneArcade(){
        super(new GameConfig(320, 240, Color.WHITE, "src/assets/", 50));

        for(int i = 0; i < 500; i++) new DVD();

        new CursorFollower();
        new BoundryBreaker();

        register();
    }

    @Override
    public void update() {
    }
}
