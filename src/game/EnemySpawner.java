package game;

import engine.Game;
import engine.math.Axis2D;
import engine.update.Updateable;
import java.util.Random;

public class EnemySpawner implements Updateable {

    Player player;

    int spawnDelayInFrames = 100;
    int frameCounter = 0;

    public EnemySpawner(Player player) {
        super();

        this.player = player;

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

        new Enemy(x, y, spriteSheetName, player);
    }

    @Override
    public void update() {
        frameCounter += 1;

        if (frameCounter == spawnDelayInFrames) {
            spawnEnemy();

            frameCounter = 0;
        }
    }
}
