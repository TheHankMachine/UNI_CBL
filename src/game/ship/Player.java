package game.ship;

import engine.Game;
import engine.input.InputHandler.InputKey;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.update.Updateable;

public class Player extends Ship implements Updateable {

    public Player() {
        super("player.png",
            Game.getInstance().getDisplay().getDisplayOriginX() + Game.getInstance().getDisplayWidth() / 2f,
            Game.getInstance().getDisplay().getDisplayOriginY() + Game.getInstance().getDisplayHeight() / 2f
        );

        // setFrame(0);

        registerUpdate();
    }

    @Override
    public void die() {
        super.die();
        setHittable(false);

        game.endGame();
    }

    @Override
    public int getShootingDelay(){
        return 6;
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
