package game;

import engine.Game;
import engine.GameConfig;
import java.awt.*;

public class PlaneArcade extends Game {
    private Player player; 
    private Clouds clouds; 
    private EnemySpawner enemySpawner;

    // private final ArrayList<Enemy> enemies = new ArrayList<>(); 
    private final MainMenu mainMenu;
    private EndScreen endScreen;

    public PlaneArcade() {
        super(new GameConfig(1280, 960, new Color(0x51a6dc), "src/assets/", 50));

        mainMenu = new MainMenu(this);

        register();
    }

    public void paly() {
        player = new Player(this);
        clouds = new Clouds(player);

        // enemySpawner = new EnemySpawner(player, this);

        if (!(mainMenu == null)) {
            mainMenu.close();
        }

        if (!(endScreen == null)) {
            endScreen.close();
        }
    }

    public void endGame() {
        // enemies.forEach((enemy) -> enemy.remove());
        clouds.destroyClouds();
        enemySpawner.deregisterUpdate();
        // enemies.clear();

        endScreen = new EndScreen(this);
    }

    // public void addEnemy(Enemy enemy) {
    //     enemies.add(enemy);
    // }

    // public void removeEnemy(Enemy enemy) {
    //     enemies.remove(enemy);
    // }

    // public ArrayList<Enemy> getEnemies() {
    //     return enemies;
    // }


    @Override
    public float getDefaultScale() {
        return 4f;
    }

    @Override
    public void update() {

    }
}
