package game.ship;

import engine.math.Vector2D;

public abstract class EnemyAI {

    protected final Enemy enemy;

    public EnemyAI(Enemy enemy){
        this.enemy = enemy;
    }

    public final void controlShip(){
        if(shoot() && enemy.canShoot()){
            enemy.shoot();
        }
        enemy.rotateToAngle(getTargetAngle());
    }

    protected abstract boolean shoot();

    protected abstract float getTargetAngle();

    protected float distanceToPlayer(){
        Vector2D vectorToPlayer = enemy.getPlayer().getPosition().copy();
        vectorToPlayer.subtract(enemy.getPosition());
        return vectorToPlayer.getLength();
    }

    protected float angleToPlayer(){
        Vector2D vectorToPlayer = enemy.getPlayer().getPosition().copy();
        vectorToPlayer.subtract(enemy.getPosition());

        return vectorToPlayer.getAngle();
    }

    protected float getAngleDifference(){
        float angleDiff = angleToPlayer() - enemy.getCurrentAngle() + (float) Math.PI / 2;

        if(angleDiff < 0){
            angleDiff += (float) Math.PI * 2;
        }

        if(Math.abs(angleDiff) > Math.PI) {
            angleDiff -= Math.signum(angleDiff) * (float) Math.PI * 2;
        }

        return angleDiff;
    }

    protected boolean facingPlayer(float tolerance) {
        return Math.abs(getAngleDifference()) <= tolerance;
    }

}