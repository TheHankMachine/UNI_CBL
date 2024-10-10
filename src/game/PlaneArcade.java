package game;

import engine.Game;
import engine.GameConfig;
import engine.example.DVD;
import java.awt.*;


public class PlaneArcade extends Game {

    public PlaneArcade(){
        super(new GameConfig(320, 240, new Color(0x51a6dc), "src/assets/", 50));

        // for(int i = 0; i < 500; i++) new DVD();

        // new CursorFollower();
        // new BoundryBreaker();
        new DVD();
        new Player();
        
        register();
    }

    @Override
    public void update() {
    }
}
