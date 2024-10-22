package game.ship;

import engine.Game;
import engine.input.InputHandler.InputKey;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.update.Updateable;
import game.PlaneArcade;

public class Player extends Ship implements Updateable {

    public Player(PlaneArcade game) {
        super("player.png",
                (float) ((Game.getInstance().getDisplayWidth() / 2)),
                (float) ((Game.getInstance().getDisplayHeight() / 2)),
                8f, 0.34f, game);

        setScale(4);

        // setting the initial player sprite
        setFrame(0);

        registerUpdate();
    }

    @Override
    public void die() {
        super.die();
        game.endGame();    
    }

    @Override
    public void update() {
        // getting mouse position
        Vector2D cursorPosition = Game.getInstance().getInput().getMousePositionGameRelative();

        rotateToVector(cursorPosition);

        move();

        if (canShoot() && Game.getInstance().getInput().isDown(InputKey.SPACE)) {
            shoot(true);
        }

        // setting the display origin to the current position of the player
        Game.getInstance().getDisplay().setDisplayOrigin(
                position.get(Axis2D.X).intValue() - Game.getInstance().getDisplayWidth() / 2,
                position.get(Axis2D.Y).intValue() - Game.getInstance().getDisplayHeight() / 2
        );
    }
}
