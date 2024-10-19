package game;

import engine.Game;
import engine.GameConfig;
import java.awt.*;
import java.util.ArrayList;

public class PlaneArcade extends Game {

    ArrayList<Enemy> enemies = new ArrayList<>(); 
    MainMenu mainMenu;

    public PlaneArcade() {
        super(new GameConfig(1280, 960, new Color(0x51a6dc), "src/assets/", 50));

        mainMenu = new MainMenu(this);

        register();
    }

    public void paly() {
        Player player = new Player(this);

        new Clouds(player);
        new EnemySpawner(player, this);

        mainMenu.close();
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
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
