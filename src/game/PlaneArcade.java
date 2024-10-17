package game;

import engine.Game;
import engine.GameConfig;
import engine.example.DVD;

import java.awt.*;

public class PlaneArcade extends Game {

    public PlaneArcade() {
        super(new GameConfig(1280, 960, new Color(0x51a6dc), "src/assets/", 50));

        //  for(int i = 0; i < 50; i++) new DVD();
        // new CursorFollower();
        // new BoundryBreaker();
//        new DVD();
        // new Cloud(new Vector2D(200, 0));
        Player player = new Player();
        new Clouds(player);
        new EnemySpawner(player);

        // new DisplayVector(new Vector2D(680, 480), new Vector2D(1000, 1000));

        register();
    }

    @Override
    public float getDefaultScale() {
        return 4f;
    }

    @Override
    public void update() {

    }
}
