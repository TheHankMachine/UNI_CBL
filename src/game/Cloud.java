package game;

import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;
import java.util.Random;

public class Cloud extends SpriteSheet implements Updateable {

    public Cloud(Vector2D position) {
        super("clouds.png", 45, 15,
                position.get(Axis2D.X).intValue(),
                position.get(Axis2D.Y).intValue());

        // Pick a random cloud sprite
        Random rand = new Random();
        int sprite_index = rand.nextInt(3);

        // Set the sprite
        setFrame(sprite_index);

        registerUpdate();
    }

    @Override
    public void update() {
    }

}
