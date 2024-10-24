package game.ship;

public class Chaser extends EnemyAI {

    private int t = 0;

    public Chaser(Enemy enemy) {
        super(enemy);
    }

    @Override
    protected boolean shoot() {
        return facingPlayer(0.6f);
    }

    @Override
    protected float getTargetAngle() {
        t++;

        if(
            t % 10 == 0 &&
            !super.facingPlayer(0.5f) &&
            super.distanceToPlayer() > 200f
        ){
            return super.angleToPlayer();
        }

        return enemy.getVelocity().getAngle();
    }
}