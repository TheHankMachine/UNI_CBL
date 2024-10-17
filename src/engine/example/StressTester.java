package engine.example;

import engine.Game;
import engine.GameConfig;

import java.awt.*;

public class StressTester extends Game {

    public static void main(String[] args){
        new StressTester(4000);
    }

    public StressTester(int numEntities) {
        super(new GameConfig(600, 600, Color.ORANGE, "src/assets/", 60));

        for(int i = 0; i < numEntities; i++){
            new DVD();
        }

        register();
    }

    @Override
    public void update() {

    }
}
