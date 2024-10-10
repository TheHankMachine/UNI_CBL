package game;

import engine.Game;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;

public class Player extends SpriteSheet implements Updateable {

    float pi = (float) Math.PI;

    float currentAngle = 0;

    public Player() {
        super("player.png", 16, 16,
                (float) (Game.getInstance().getDisplayWidth() / 2),
                (float) (Game.getInstance().getDisplayHeight() / 2));

        setOrigin(0, 0);
        setFrame(0);

        registerUpdate();
    }

    @Override
    public void update() {
        Vector2D cursorPosition = Game.getInstance().getInput().getMousePositionScreenRelative();

        int screenWidth = Game.getInstance().getDisplayWidth();
        int screenHeight = Game.getInstance().getDisplayHeight();

        float rotationSpeed = 0.34f;
        float rotationStep = pi / 8;

        cursorPosition.add(
                -(screenWidth / 2),
                -(screenHeight / 2)
        );

        float angle = cursorPosition.getAngle() + pi / 2;
        if (angle < 0) {
            angle = 2 * pi + angle;
        }

        float diff = currentAngle - angle;

        if (Math.abs(diff) > pi) {
            diff -= 2 * pi * Math.signum(diff);
        }

        currentAngle -= Math.signum(diff) * rotationSpeed;

        if (currentAngle < 0) {
            currentAngle += 2 * pi;
        }

        if (Math.abs(diff) <= rotationStep) {
            currentAngle = angle;
        }

        int sprite_index = (int) (currentAngle / rotationStep);

        System.out.println(Math.cos(currentAngle));

        // cursorPosition.normalise();
        // move(cursorPosition);

        // Vector2D directionVector = cursorPosition;
        // directionVector.subtract(position);

        // move(directionVector);

        setFrame(sprite_index);
    }
}
