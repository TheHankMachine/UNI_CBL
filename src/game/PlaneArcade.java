package game;

import engine.Game;
import engine.GameConfig;
import engine.math.Vector2D;
import java.awt.*;

public class PlaneArcade extends Game {

    public PlaneArcade() {
        super(new GameConfig(640, 480, new Color(0x51a6dc), "src/assets/", 50));

        //  for(int i = 0; i < 50; i++) new DVD();
        // new CursorFollower();
        // new BoundryBreaker();
//        new DVD();
        new Cloud(new Vector2D(0, 0));
        new Player();

        register();
    }

    @Override
    public void update() {

    }
}
