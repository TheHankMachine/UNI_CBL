package game.ship;

import engine.Game;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;
import game.Bullet;
import game.PlaneArcade;
import game.effect.Explosion;

public class Ship extends SpriteSheet implements Updateable {

    private final float PI = (float) Math.PI;
    public static float SPEED = 8f;

    private final float rotationStep = PI / 8;
    private final Vector2D directionVector = new Vector2D();

    private int frameCounter = 0;

    private float currentAngle = PI / 2;
    private float fixedAngle = 0;
    private final float rotationSpeed = PI / 24;

    private boolean hittable = true;

    protected PlaneArcade game;

    public Ship(String spriteSheetName, float x, float y) {
        super(spriteSheetName, 16, 16, x, y);

        this.game = (PlaneArcade) Game.getInstance();
    }

    /**
     *
     * @return how much cursors angle if offset from the actual angle of the
     * plane
     */
    public float cursorAngleOffset() {
        return rotationStep / 2;
    }

    /**
     * @param value
     * @return sets hittable to provided boolean value
     */
    public void setHittable(boolean value) {
        hittable = value;
    }

    /**
     *
     * @return true if the ship is hittable, false otherwise
     */
    public boolean isHittable() {
        return hittable;
    }

    /**
     * Sets a random rotation angle of the ship.
     *
     * @return void
     */
    public void setRandomInitialAngle() {
        int spriteIndex = (int) (Math.random() * 16);
        setFrame(spriteIndex);

        currentAngle = spriteIndex * rotationStep;
    }

    /**
     * Increases the score gained by the player by 100.
     *
     * @return void
     */
    public void increaseScore() {
        game.increaseScore(100);
    }

    /**
     * Rotates the sprite to the given angle relative to the X-axis.
     *
     * @param angle value in radians to which the ship should rotate
     *
     * @return void
     */
    public void rotateToAngle(float angle) {
        // calculating the angle between the X-axis and the vector from the center of the screen to the cursor
        angle += PI / 2 + rotationStep / 2;

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
        int spriteIndex = (int) (currentAngle / rotationStep);

        // setting the sprite sheet frame to the calculated index
        setFrame(spriteIndex);
    }

    /**
     *
     * Rotates the ship to the angle between the X-axis and the line from (0, 0)
     * to (vectorX, vectorY).
     *
     * @param vector point towards which the ship should rotate
     *
     * @return void
     */
    public void rotateToVector(Vector2D vector) {
        // adjusting the position to be relative to the player
        vector.subtract(position);
        vector.subtract(new Vector2D(8, 8));

        rotateToAngle(vector.getAngle());
    }

    /**
     *
     * @return the current rotation of the ship in radians
     */
    public float getCurrentAngle() {
        return fixedAngle;
    }

    /**
     *
     * @return copy of the vector along which the ship is currently moving
     */
    public Vector2D getVelocity() {
        return directionVector.copy();
    }

    /**
     * Moves the ship towards the direction that it is facing with set speed.
     * 
     * @return void 
     */
    public void move() {
        // the player will move in the direction that the sprite is facing
        fixedAngle = currentAngle - (currentAngle % rotationStep);

        // calculating the vector along which the player will move
        directionVector.setTo(
                (float) Math.sin(fixedAngle),
                (float) -Math.cos(fixedAngle)
        );

        // scaling the vector to make the player move with the desired speed
        directionVector.scale(SPEED);

        // moving the player
        move(directionVector);
    }

    /**
     *
     * @return true if the ship is currently able to shoot, false otherwise
     */
    public boolean canShoot() {
        // reset the counter after waiting
        if (frameCounter == getShootingDelay()) {
            frameCounter = 0;
        }

        // increase the counter
        if (frameCounter > 0) {
            frameCounter++;
            return false;
        }

        return true;
    }

    /**
     * Instantiates a bullet that moves along the ship's direction vector.
     * 
     * @return void 
     */
    public void shoot(boolean shotByPlayer) {
        // start the counter
        frameCounter = 1;

        // the position of the bullet
        Vector2D bulletPosition = position.copy();
        bulletPosition.add(directionVector);

        // instantiate the bullet
        new Bullet(bulletPosition.get(Axis2D.X).intValue(),
                bulletPosition.get(Axis2D.Y).intValue(), directionVector, shotByPlayer);
    }

    /**
     * Renders an explosion at the ship's position.
     * Removes the ship from render list and update list.
     * 
     * @return void
     */
    public void die() {
        new Explosion(position.get(Axis2D.X).intValue(), position.get(Axis2D.Y).intValue());

        deregisterRender();
        deregisterUpdate();
    }

    /**
     *
     * @return how many frames the ship has to wait before shooting another bullet
     */
    public int getShootingDelay() {
        return 10;
    }

    @Override
    public void update() {

    }

    @Override
    public DepthLayer getDepth() {
        return DepthLayer.MIDFORGROUND;
    }
}
