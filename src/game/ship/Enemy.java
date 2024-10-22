
package game.ship;

import engine.Game;
import engine.math.Collideable;
import engine.math.Vector2D;
import engine.update.Updateable;
import game.EnemyAI;
import game.PlaneArcade;


public class Enemy extends Ship implements Updateable {

    private final Player player;

    private boolean hittable = true;
    private EnemyAI AI;

    public Enemy(int x, int y, String spriteSheetName, PlaneArcade game) {
        super(spriteSheetName, x, y, 8f, 0.3f, game);

        this.player = game.getPlayer();

        AI = new EnemyAI.Chaser(this);

        setRandomInitialAngle();

        registerUpdate();
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

    public void shoot(){
        super.shoot(false);
    }

    public Player getPlayer(){
        return player;
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

        AI.controlShip();

//        if (canShoot() && facingPlayer()) {
//            shoot(false);
//        }

        move();
        screenWrap();
    }
}
