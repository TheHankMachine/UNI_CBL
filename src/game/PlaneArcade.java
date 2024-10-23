package game;

import engine.Game;
import engine.GameConfig;
import engine.math.Axis2D;
import game.effect.Clouds;
import game.ship.Enemy;
import game.ship.Player;
import game.ship.Ship;

import java.awt.*;
import java.util.ArrayList;

public class PlaneArcade extends Game {
    private Clouds clouds;
    private Player player;
    private EnemySpawner enemySpawner;

    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final MainMenu mainMenu;
    private EndScreen endScreen;
    private int enemyCounter = 0;

    private boolean active = false;

    public PlaneArcade() {
        super(new GameConfig(1280, 960, new Color(0x51a6dc), "src/assets/", 50));

        mainMenu = new MainMenu(this);

        clouds = new Clouds();

        register();
    }

    public void play() {
        active = true;
        player = new Player();

        enemySpawner = new EnemySpawner(this);

        if (!(mainMenu == null)) {
            mainMenu.close();
        }

        if (!(endScreen == null)) {
            endScreen.close();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void endGame() {
        enemies.forEach((enemy) -> enemy.remove());
//        clouds.destroyClouds();
        enemySpawner.deregisterUpdate();
        enemies.clear();

        endScreen = new EndScreen(this);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public int getEnemyCounter() {
        return enemyCounter;
    }

    public void setEnemyCounter(int value) {
        enemyCounter = value;
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
        if(!active){
            getDisplay().getDisplayOrigin().add(Axis2D.X, Ship.SPEED);
        }
    }
}
