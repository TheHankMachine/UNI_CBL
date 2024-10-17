package game;

import engine.Game;
import engine.GameConfig;
import java.awt.*;
import java.util.ArrayList;

public class PlaneArcade extends Game {

    ArrayList<Enemy> enemies = new ArrayList<>(); 

    public PlaneArcade() {
        super(new GameConfig(1280, 960, new Color(0x51a6dc), "src/assets/", 50));

        Player player = new Player(this);

        new Clouds(player);
        new EnemySpawner(player, this);

        register();
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public float getDefaultScale() {
        return 4f;
    }

    @Override
    public void update() {

    }
}
