package game;

import engine.render.SpriteSheet;
import java.util.Random;

public class Cloud extends SpriteSheet{

    public Cloud(int x, int y) {
        super("clouds.png", 45, 15, x, y);

        // Pick a random cloud sprite
        Random rand = new Random();
        int sprite_index = rand.nextInt(3);

        setOrigin(0, 0);

        // Set the sprite
        setFrame(sprite_index);

    }

    @Override
    public DepthLayer getDepth() {
        return DepthLayer.BACKGROUND;
    }


}
