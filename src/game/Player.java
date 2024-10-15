package game;

import engine.Game;
import engine.input.Input.InputKey;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.update.Updateable;

public class Player extends Ship implements Updateable {

    // position passed to enemies
    private Vector2D oldPosition;

    private int frameCounter = 0;
    private int oldPositionDelay = 75;

    public Player() {
        super("player.png",
                (float) ((Game.getInstance().getDisplayWidth() / 2)),
                (float) ((Game.getInstance().getDisplayHeight() / 2)),
                12f);

        oldPosition = position.copy();

        setScale(4);

        // setting the initial player sprite
        setFrame(0);

        registerUpdate();
    }

    public Vector2D getOldPosition() {
        return oldPosition.copy();
    }

    private void updateOldPosition() {
        if (frameCounter == oldPositionDelay) {
            oldPosition = position.copy();
            frameCounter = 0;
        }

        frameCounter++;
    }

    @Override
    public void update() {
        // getting mouse position
        Vector2D cursorPosition = Game.getInstance().getInput().getMousePositionGameRelative();

        rotateToVector(cursorPosition);

        move();

        updateOldPosition();

        if (canShoot() && Game.getInstance().getInput().isDown(InputKey.SPACE)) {
            shoot();
        }

        // setting the display origin to the current position of the player
        Game.getInstance().getDisplay().setDisplayOrigin(
                position.get(Axis2D.X).intValue() - Game.getInstance().getDisplayWidth() / 2,
                position.get(Axis2D.Y).intValue() - Game.getInstance().getDisplayHeight() / 2
        );
    }
}
