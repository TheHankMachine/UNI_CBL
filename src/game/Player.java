package game;

import engine.Game;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;

public class Player extends SpriteSheet implements Updateable {

    // PI
    private final float PI = (float) Math.PI;

    // the angle of player's rotation
    float currentAngle = 0;

    // the speed of player's rotation
    private final float rotationSpeed = 0.34f;

    // the minimum angle of player's rotation
    private final float rotationStep = PI / 8;

    // the speed of player's movement
    private final float speed = 2f;

    public Player() {
        super("player.png", 16, 16,
                (float) (Game.getInstance().getDisplayWidth() / 2),
                (float) (Game.getInstance().getDisplayHeight() / 2));

        // setting the origin of coordinate system
        setOrigin(0, 0);

        // setting the initial player sprite
        setFrame(0);

        registerUpdate();
    }

    private void rotateToCursor() {
        // getting mouse position
        Vector2D cursorPosition = Game.getInstance().getInput().getMousePositionGameRelative();

        // adjusting the cursor position to be relative to the player
        cursorPosition.subtract(position);

        // calculating the angle between the X-axis and the vector from the center of the screen to the cursor
        float angle = cursorPosition.getAngle() + PI / 2;

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

    private void move() {
        // calculating the vector along which the player will move
        Vector2D directionVector = new Vector2D(
                (float) Math.sin(currentAngle),
                (float) -Math.cos(currentAngle));

        // scaling the vector to make the player move with the desired speed
        directionVector.scale(speed);

        // moving the player
        move(directionVector);

        // setting the display origin to the current position of the player
        Game.getInstance().getDisplay().setDisplayOrigin(
                position.get(Axis2D.X).intValue() - Game.getInstance().getDisplayWidth() / 2,
                position.get(Axis2D.Y).intValue() - Game.getInstance().getDisplayHeight() / 2
        );
    }

    private void renderClouds() {

    }

    @Override
    public void update() {
        // rotating the player
        rotateToCursor();
        
        // moving the player
        move();

        // rendering clouds
        renderClouds();
    }
}
