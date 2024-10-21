package game;

import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;

public class Ship extends SpriteSheet implements Updateable {

    private final float PI = (float) Math.PI;

    private final float rotationStep = PI / 8;

    private final int shootingDelayInFrames = 5;
    private int frameCounter = 0;

    private Vector2D directionVector;
    private float currentAngle = 0;
    private float fixedAngle = 0;
    private float speed = 8f;
    private float rotationSpeed = 0.34f;

    protected PlaneArcade game;

    public Ship(String spriteSheetName, float x, float y, float speed, float rotationSpeed, PlaneArcade game) {
        super(spriteSheetName, 16, 16, x, y);

        this.speed = speed;
        this.rotationSpeed = rotationSpeed;  
        this.game = game;
    }

    public float cursorAngleOffset() {
        return rotationStep / 2;
    }

    public void rotateToVector(Vector2D vector) {
        // adjusting the cursor position to be relative to the player
        vector.subtract(position);
        vector.subtract(new Vector2D(8, 8));

        // calculating the angle between the X-axis and the vector from the center of the screen to the cursor
        float angle = vector.getAngle() + PI / 2 + rotationStep / 2;

        // converting negative angle values to positive ones
        if (angle < 0) {
            angle = 2 * PI + angle;
        }

        // the difference between the current angle and the angle we want to reach (we use it to make a smooth rotation)
        float diff = currentAngle - angle;

        // handling difference values greater than PI
        if (Math.abs(diff) > PI) {
            diff -= 2 * PI * Math.signum(diff);
        }

        // changing the current angle
        currentAngle -= Math.signum(diff) * rotationSpeed;

        // handling negative angle values
        if (currentAngle < 0) {
            currentAngle += 2 * PI;
        }

        // not changing the current angle if it is lesser or equal to the minimal rotation angle
        if (Math.abs(diff) <= rotationStep) {
            currentAngle = angle;
        }

        // calculating the sprite sheet frame
        int sprite_index = (int) (currentAngle / rotationStep);

        // setting the sprite sheet frame to the calculated index
        setFrame(sprite_index);
    }

    public Vector2D getVelocity() {
        return directionVector.copy();
    }

    public void move() {
        // the player will move in the direction that the sprite is facing
        fixedAngle = currentAngle - (currentAngle % rotationStep);

        // calculating the vector along which the player will move
        directionVector = new Vector2D(
                (float) Math.sin(fixedAngle),
                (float) -Math.cos(fixedAngle));

        // scaling the vector to make the player move with the desired speed
        directionVector.scale(speed);

        // moving the player
        move(directionVector);
    }

    public boolean canShoot() {
        // reset the counter after waiting
        if (frameCounter == shootingDelayInFrames) {
            frameCounter = 0;
        }

        // increase the counter
        if (frameCounter > 0) {
            frameCounter++;
            return false;
        }

        return true;
    }

    public void shoot() {
        // start the counter
        frameCounter = 1;

        // the position of the bullet
        Vector2D bulletPosition = position.copy();
        bulletPosition.add(directionVector);

        // instantiate the bullet
        new Bullet(bulletPosition.get(Axis2D.X).intValue(),
                bulletPosition.get(Axis2D.Y).intValue(), directionVector, game);
    }

    public void die() {
        new Explosion(position.get(Axis2D.X).intValue(), position.get(Axis2D.Y).intValue());

        deregisterRender();
        deregisterUpdate();
    }

    @Override
    public void update() {

    }

    @Override
    public DepthLayer getDepth() {
        return DepthLayer.FOREGROUND;
    }
}
