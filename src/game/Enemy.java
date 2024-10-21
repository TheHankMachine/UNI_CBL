package game;

import engine.math.Collideable;
import engine.math.Vector2D;
import engine.update.Updateable;
import java.util.Random;

public class Enemy extends Ship implements Updateable {

    private final Player player;
    // private Vector2D directionVector;

    private int frameCounter = 0;
    private final int oldPositionDelay = 100;

    public Enemy(int x, int y, String spriteSheetName, Player player, PlaneArcade game) {
        super(spriteSheetName, x, y, 8f, 0.3f, game);

        this.player = player;

        Random rand = new Random();
        rotateToVector(new Vector2D(rand.nextFloat(), rand.nextFloat()));

        registerUpdate();
    }

    private void updateOldPosition() {
        if (frameCounter == oldPositionDelay) {
            rotateToVector(player.getOldPosition());
            frameCounter = 0;
        }

        frameCounter++;
    }

    public void remove() {
        deregisterRender();
        deregisterUpdate();
        // game.removeEnemy(this);
    }

    @Override
    public void die() {
        super.die();
        // game.removeEnemy(this);
    }

    @Override
    public void update() {
        if (Collideable.collides(this, player)) {
            die();
            player.die();
        }

        updateOldPosition();
        move();
    }
}
