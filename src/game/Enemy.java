package game;

import engine.math.Vector2D;
import engine.update.Updateable;
import java.util.Random;

public class Enemy extends Ship implements Updateable {

    private final Player player;
    // private Vector2D directionVector;

    private int frameCounter = 0;
    private final int oldPositionDelay = 10;

    public Enemy(int x, int y, String spriteSheetName, Player player) {
        super(spriteSheetName, x, y, 12f, 0.1f);

        this.player = player;  

        Random rand = new Random();
        rotateToVector(new Vector2D(rand.nextFloat(), rand.nextFloat()));

        registerUpdate();
    }

    private void updateOldPosition() {
        if (frameCounter == oldPositionDelay) {
            rotateToVector(player.getPosition().copy());
            frameCounter = 0;
        }

        frameCounter++;
    }

    @Override
    public void update() {
        // rotateToVector(player.getOldPosition());
        updateOldPosition();
        move();
    }
}
