package game;

import engine.Game;
import engine.math.Axis2D;
import engine.update.Updateable;
import game.ship.Enemy;
import game.ship.Player;

import java.util.Random;

public class EnemySpawner implements Updateable {

    private final Player player;
    private final PlaneArcade game;

    private final int maxEnemies = 10;
    private final int spawnDelayInFrames = 50;
    private int frameCounter = 0;
    

    public EnemySpawner(PlaneArcade game) {
        super();

        this.game = game;
        this.player = game.getPlayer();

        registerUpdate();
    }

    private String randomSpriteSheet(Random rand) {
        boolean secondSpriteSheet = rand.nextBoolean();

        String spriteSheetName = "enemy";

        if (secondSpriteSheet) {
            spriteSheetName += "2";
        }

        return spriteSheetName + ".png";  
    }

    private void spawnEnemy() {
        int playerX = player.getPosition().get(Axis2D.X).intValue();
        int playerY = player.getPosition().get(Axis2D.Y).intValue();

        Random rand = new Random();

        int spawnSide = rand.nextInt(4);

        int screenWidth = Game.getInstance().getDisplayWidth();
        int screenHeight = Game.getInstance().getDisplayHeight();

        int randomX = (playerX - screenWidth / 2)
                + rand.nextInt((playerX + screenWidth / 2)
                        - (playerX - screenWidth / 2));

        int randomY = (playerY - screenHeight / 2)
                + rand.nextInt((playerY + screenHeight / 2)
                        - (playerY - screenHeight / 2));

        int x;
        int y;

        switch (spawnSide) {
            // top
            case 0:
                x = randomX;
                y = playerY - screenHeight;

                break;
            // bottom
            case 1:
                x = randomX;
                y = playerY + screenHeight;

                break;
            // left
            case 2:
                x = playerX - screenWidth;
                y = randomY;

                break;
            // right
            case 3:
                x = playerX + screenWidth;
                y = randomY;

                break;
            default:
                throw new AssertionError();
        }

        String spriteSheetName = randomSpriteSheet(rand);

        Enemy enemy = new Enemy(x, y, spriteSheetName);
        game.addEnemy(enemy);
    }

    @Override
    public void update() {
        frameCounter++;

        if (frameCounter == spawnDelayInFrames) {
            int enemyCounter = game.getEnemyCounter();

            if (enemyCounter < maxEnemies) {
                spawnEnemy();
                game.setEnemyCounter(enemyCounter + 1);
            }

            frameCounter = 0;
        }
    }
}
