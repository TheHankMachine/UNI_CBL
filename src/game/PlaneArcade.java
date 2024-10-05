package game;

import engine.Game;
import engine.GameConfig;
import engine.defaults.DVD;

import java.awt.*;

public class PlaneArcade extends Game {

    public PlaneArcade(){
        super(new GameConfig(320, 240, Color.ORANGE));

        for(int i = 0; i < 1_000; i++) new DVD();

        register();
    }

    @Override
    public void update() {

    }
}
