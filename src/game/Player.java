package game;

import engine.Game;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;

public class Player extends SpriteSheet implements Updateable {

    private final float PI = (float) Math.PI;

    float currentAngle = 0;

    private final float rotationSpeed = 0.34f;
    private final float rotationStep = PI / 8;

    private final float speed = 2f;

    private final Vector2D cameraPosition = new Vector2D();
    Vector2D screenCenter = new Vector2D();

    public Player() {
        super("player.png", 16, 16,
                (float) (Game.getInstance().getDisplayWidth() / 2),
                (float) (Game.getInstance().getDisplayHeight() / 2));

        Game.getInstance().getDisplay().setDisplayOriginReference(cameraPosition);
        setOrigin(0, 0);
        setFrame(0);

        screenCenter = new Vector2D(
                Game.getInstance().getDisplayWidth() / 2,
                Game.getInstance().getDisplayHeight() / 2);

        registerUpdate();
    }

    @Override
    public void update() {
        // You probably want game relative
        Vector2D cursorPosition = Game.getInstance().getInput().getMousePositionGameRelative();
        cursorPosition.subtract(position);

        float angle = cursorPosition.getAngle() + PI / 2;

        if (angle < 0) {
            angle = 2 * PI + angle;
        }

        float diff = currentAngle - angle;

        if (Math.abs(diff) > PI) {
            diff -= 2 * PI * Math.signum(diff);
        }

        currentAngle -= Math.signum(diff) * rotationSpeed;

        if (currentAngle < 0) {
            currentAngle += 2 * PI;
        }

        if (Math.abs(diff) <= rotationStep) {
            currentAngle = angle;
        }

        int sprite_index = (int) (currentAngle / rotationStep);

        Vector2D directionVector = new Vector2D(
                (float) Math.sin(currentAngle),
                (float) -Math.cos(currentAngle));

        directionVector.scale(speed);

        move(directionVector);

        setFrame(sprite_index);

        // IDK what I am doing. Ignore this shit.
        // screenCenter.setTo(position);
        // screenCenter.setTo(position);
        // System.out.println(screenCenter);

        // Lemme help you out here
        // set display origin is in display, which you can access by
        // writing this very concise line of code:
        Game.getInstance().getDisplay().setDisplayOrigin(
                position.get(Axis2D.X).intValue() - Game.getInstance().getDisplayWidth() / 2,
                position.get(Axis2D.Y).intValue() - Game.getInstance().getDisplayHeight() / 2
        );

        // setOrigin(originX - directionVector.get(Axis2D.X), originY - directionVector.get(Axis2D.Y));
        // cameraPosition.setTo(screenCenter);
        // cameraPosition.subtract(screenCenter);
    }
}
