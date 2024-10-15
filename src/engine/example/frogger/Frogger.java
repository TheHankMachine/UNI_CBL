package engine.example.frogger;

import engine.Game;
import engine.GameConfig;

import java.awt.*;

public class Frogger extends Game {

    public static final int GRID_WIDTH = 12;
    public static final int GRID_HEIGHT = 16;
    public static final int GRID_SIZE = 12;
    public static final float MOVE_SPEED = 2f;

    public static void main(String[] args){
        new Frogger();
    }

    public Frogger(){
        super(new GameConfig(GRID_WIDTH * GRID_SIZE, GRID_HEIGHT * GRID_SIZE, Color.PINK, "./src/assets/", 50));

        new Map();
        new Frog(12 * 5.5f, 12 * 8.5f);

        register();
    }

    @Override
    public void update() {

    }
}
