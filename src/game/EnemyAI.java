package game;

import engine.math.Vector2D;
import game.ship.Enemy;

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

    abstract boolean shoot();

    abstract float getTargetAngle();

    private float distanceToPlayer(){
        Vector2D vectorToPlayer = enemy.getPlayer().getPosition().copy();
        vectorToPlayer.subtract(enemy.getPosition());
        return vectorToPlayer.getLength();
    }

    private float angleToPlayer(){
        Vector2D vectorToPlayer = enemy.getPlayer().getPosition().copy();
        vectorToPlayer.subtract(enemy.getPosition());

        return vectorToPlayer.getAngle();
    }

    private float getAngleDifference(){
        float angleDiff = angleToPlayer() - enemy.getCurrentAngle() + (float) Math.PI / 2;

        if(angleDiff < 0){
            angleDiff += (float) Math.PI * 2;
        }

        if(Math.abs(angleDiff) > Math.PI) {
            angleDiff -= Math.signum(angleDiff) * (float) Math.PI * 2;
        }

        return angleDiff;
    }

    private boolean facingPlayer(float tolerance) {
        return Math.abs(getAngleDifference()) <= tolerance;
    }

    public static class Chaser extends EnemyAI {

        private int t = 0;

        public Chaser(Enemy enemy) {
            super(enemy);
        }

        @Override
        boolean shoot() {
            return super.facingPlayer(0.6f);
        }

        @Override
        float getTargetAngle() {
            t++;

            if(
                t % 10 == 0 &&
                !super.facingPlayer(1f) &&
                super.distanceToPlayer() > 200f
            ){
                return super.angleToPlayer();
            }

//            if(){
//            }
//
            return enemy.getVelocity().getAngle();
        }
    }

}