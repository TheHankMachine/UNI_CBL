package game;

import engine.Game;
import engine.input.Input.InputKey;
import engine.math.Vector2D;
import engine.update.Updateable;

public class Player extends Ship implements Updateable {

    // // PI
    // private final float PI = (float) Math.PI;

    // // the angle of player's rotation
    // float currentAngle = 0;

    // // the speed of player's rotation
    // private final float rotationSpeed = 0.34f;

    // // the minimum angle of player's rotation
    // private final float rotationStep = PI / 8;

    // // the speed of player's movement
    // private final float speed = 8f;

    // int shootingDelayInFrames = 10;
    // int frameCounter = 0;

    public Player() {
        super("player.png",
                (float) ((Game.getInstance().getDisplayWidth() / 2)),
                (float) ((Game.getInstance().getDisplayHeight() / 2)));

        setScale(4);

        // setting the initial player sprite
        setFrame(0);

        registerUpdate();
    }

    // private void rotateToVector(Vector2D vector) {
    //     // adjusting the cursor position to be relative to the player
    //     vector.subtract(position);
    //     vector.subtract(new Vector2D(8, 8));

    //     // calculating the angle between the X-axis and the vector from the center of the screen to the cursor
    //     float angle = vector.getAngle() + PI / 2;

    //     // converting negative angle values to positive ones
    //     if (angle < 0) {
    //         angle = 2 * PI + angle;
    //     }

    //     // the difference between the current angle and the angle we want to reach (we use it to make a smooth rotation)
    //     float diff = currentAngle - angle;

    //     // handling difference values greater than PI
    //     if (Math.abs(diff) > PI) {
    //         diff -= 2 * PI * Math.signum(diff);
    //     }

    //     // changing the current angle
    //     currentAngle -= Math.signum(diff) * rotationSpeed;

    //     // handling negative angle values
    //     if (currentAngle < 0) {
    //         currentAngle += 2 * PI;
    //     }

    //     // not changing the current angle if it is lesser or equal to the minimal rotation angle
    //     if (Math.abs(diff) <= rotationStep) {
    //         currentAngle = angle;
    //     }

    //     // calculating the sprite sheet frame
    //     int sprite_index = (int) (currentAngle / rotationStep);

    //     // setting the sprite sheet frame to the calculated index
    //     setFrame(sprite_index);
    // }

    // Vector2D directionVector;

    // public Vector2D getVelocity() {
    //     return directionVector.copy();
    // }

    // float fixedAngle = 0;

    // private void move() {
    //     // the player will move in the direction that the sprite is facing
    //     fixedAngle = currentAngle - (currentAngle % rotationStep);

    //     // calculating the vector along which the player will move
    //     directionVector = new Vector2D(
    //             (float) Math.sin(fixedAngle),
    //             (float) -Math.cos(fixedAngle));

    //     // scaling the vector to make the player move with the desired speed
    //     directionVector.scale(speed);

    //     // moving the player
    //     move(directionVector);

    //     // setting the display origin to the current position of the player
    //     Game.getInstance().getDisplay().setDisplayOrigin(
    //             position.get(Axis2D.X).intValue() - Game.getInstance().getDisplayWidth() / 2,
    //             position.get(Axis2D.Y).intValue() - Game.getInstance().getDisplayHeight() / 2
    //     );
    // }

    // private void handleShooting(boolean trigger) {
    //     // reset the counter after waiting
    //     if (frameCounter == shootingDelayInFrames) {
    //         frameCounter = 0;
    //     }

    //     // increase the counter
    //     if (frameCounter > 0) {
    //         frameCounter += 1;
    //         return;
    //     }

    //     // if space is pressed, instantiate a bullet
    //     if (trigger) {
    //         // start the counter
    //         frameCounter = 1;

    //         // the position of the bullet
    //         Vector2D bulletPosition = position.copy();
    //         bulletPosition.add(directionVector);

    //         // instantiate the bullet
    //         new Bullet(bulletPosition.get(Axis2D.X).intValue(), bulletPosition.get(Axis2D.Y).intValue(), directionVector);
    //     }
    // }

    @Override
    public void update() {
        // getting mouse position
        Vector2D cursorPosition = Game.getInstance().getInput().getMousePositionGameRelative();

        rotateToVector(cursorPosition);

        move();

        if(canShoot() && Game.getInstance().getInput().isDown(InputKey.SPACE)){
            shoot();
        }
    }

    @Override
    public DepthLayer getDepth() {
        return DepthLayer.FOREGROUND;
    }
}
