package game;

import engine.Game;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;

public class Player extends SpriteSheet implements Updateable {
    float pi = (float) Math.PI;

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

        cursorPosition.add(
            -(screenWidth / 2),
            -(screenHeight / 2)
        );

        float angle = (cursorPosition.getAngle() + pi / 2)* 180 / 3.14159f; 
        System.out.println( angle );
    }
}
