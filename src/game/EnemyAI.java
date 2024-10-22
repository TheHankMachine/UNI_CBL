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

        private float offset = (float) (Math.random() * Math.PI / 2);

        public Chaser(Enemy enemy) {
            super(enemy);
        }

        @Override
        boolean shoot() {
            return super.facingPlayer(0.2f);
        }

        @Override
        float getTargetAngle() {
            return enemy.getVelocity().getAngle();

//            if(super.distanceToPlayer() < 300){
//                return super.angleToPlayer() - (float) Math.PI;
//            }
//
//            return super.angleToPlayer();

//                return enemy.getVelocity().getAngle();
//            }
//            return super.facingPlayer(0.3f);
        }
    }

}