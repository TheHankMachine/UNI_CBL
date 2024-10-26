package game.ship;

import engine.Game;
import engine.math.Collideable;

public class Enemy extends Ship {

    private final Player player;

    private final EnemyAI AI;

    public Enemy(int x, int y, String spriteSheetName) {
        super(spriteSheetName, x, y);

        this.player = game.getPlayer();

        AI = new Chaser(this);

        setRandomInitialAngle();

        registerUpdate();
    }

    /**
     * Decrements enemy counter by one.
     *
     * @return void
     *
     */
    private void subtractFromEnemyCounter() {
        int enemyCounter = game.getEnemyCounter();

        game.setEnemyCounter(enemyCounter - 1);
    }

    /**
     * Removes the enemy.
     *
     * @return void
     */
    public void remove() {
        deregisterRender();
        deregisterUpdate();

        setHittable(false);

        subtractFromEnemyCounter();
    }

    /**
     * Renders a bullet that moves along the enemy's direction vector.
     *
     * @return void
     */
    public void shoot() {
        super.shoot(false);
    }

    /**
     *
     * @return the player instance
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Whenever an enemy moves outside the screen this method moves it to the
     * other side and makes it face the opposite direction.
     *
     * @return void
     */
    private void screenWrap() {
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
        setHittable(false);

        subtractFromEnemyCounter();
    }

    @Override
    public int getShootingDelay() {
        return 15;
    }

    @Override
    public void update() {
        if (Collideable.collides(this, player.getPosition())) {
            die();
            player.die();
        }

        move();
        screenWrap();

        AI.controlShip();
    }
}
