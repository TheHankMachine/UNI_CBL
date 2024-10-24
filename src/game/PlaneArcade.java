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

    // private Clouds clouds;
    private Player player;
    private EnemySpawner enemySpawner;
    private Text scoreText;

    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final MainMenu mainMenu;
    private EndScreen endScreen;
    private int enemyCounter = 0;
    private int score = 0;

    private boolean active = false;

    public PlaneArcade() {
        super(new GameConfig(1280, 960, new Color(0x51a6dc), "src/assets/", 50));

        mainMenu = new MainMenu(this);

        new Clouds();

        register();
    }

    public void play() {
        if (active) {
            return;
        }

        active = true;
        player = new Player();

        enemySpawner = new EnemySpawner(this);
        scoreText = new Text("score:" + Integer.toString(score),
         Game.getInstance().getDisplayWidth() * 0.85f,
         Game.getInstance().getDisplayHeight() * 0.05f);

        scoreText.setScale(3f);

        if (!(mainMenu == null)) {
            mainMenu.close();
        }

        if (!(endScreen == null)) {
            endScreen.close();
        }
    }

    public void increaseScore(int increment) {
        score += increment;
        
        scoreText.setText("score:" + Integer.toString(score));
    }

    public Player getPlayer() {
        return player;
    }

    public void endGame() {
        if (!active) {
            return;
        }

        active = false;

        enemies.forEach((enemy) -> enemy.remove());
        enemySpawner.deregisterUpdate();
        enemies.clear();
        scoreText.deregisterRender();

        endScreen = new EndScreen(this, score);

        score = 0;
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
        if (!active) {
            getDisplay().getDisplayOrigin().add(Axis2D.X, Ship.SPEED);
        }
    }
}
