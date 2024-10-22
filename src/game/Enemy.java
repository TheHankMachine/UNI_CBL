
package game;

import engine.Game;
import engine.math.Collideable;
import engine.update.Updateable;
import java.util.Random;


public class Enemy extends Ship implements Updateable {

    private final Player player;

    private boolean hittable = true;

    public Enemy(int x, int y, String spriteSheetName, Player player, PlaneArcade game) {
        super(spriteSheetName, x, y, 8f, 0.3f, game);

        this.player = player;

        setRandomInitialAngle();

        registerUpdate();
    }

    private void setRandomInitialAngle() {
        Random rand = new Random();

        // float x = rand.nextFloat();
        // float y = rand.nextFloat();

        // if (rand.nextBoolean()) {
        //     x *= -1;
        // }

        // if (rand.nextBoolean()) {
        //     y *= -1;
        // }

        int spriteIndex = rand.nextInt(16);
        setFrame(spriteIndex);
    }

    private void subtractFromEnemyCounter() {
        int enemyCounter = game.getEnemyCounter();

        game.setEnemyCounter(enemyCounter - 1);
    }

    public void remove() {
        deregisterRender();
        deregisterUpdate();
        hittable = false;

        subtractFromEnemyCounter();
    }

    public boolean isHittable() {
        return hittable;
    }

    public void screenWrap() {
        int screenWidth = Game.getInstance().getDisplayWidth();
        int screenHeight = Game.getInstance().getDisplayHeight();

        if (getX() < player.getX() - screenWidth / 2 - getWidth()) {
            setPosition(player.getX() + screenWidth / 2 + getWidth(), getY());
            rotateToVector(player.getPosition().copy());
        }

        if (getX() > player.getX() + screenWidth / 2 + getWidth()) {
            setPosition(player.getX() - screenWidth / 2 - getWidth(), getY());
            rotateToVector(player.getPosition().copy());
        }

        if (getY() < player.getY() - screenHeight / 2 - getHeight()) {
            setPosition(getX(), player.getY() + screenHeight / 2 + getHeight());
            rotateToVector(player.getPosition().copy());
        }

        if (getY() > player.getY() + screenHeight / 2 + getHeight()) {
            setPosition(getX(), player.getY() - screenHeight / 2 - getHeight());
            rotateToVector(player.getPosition().copy());
        }
    }

    @Override
    public void die() {
        super.die();
        hittable = false;

        subtractFromEnemyCounter();
    }

    @Override
    public void update() {
        if (Collideable.collides(this, player)) {
            die();
            player.die();
        }

        move();
        screenWrap();
    }
}
