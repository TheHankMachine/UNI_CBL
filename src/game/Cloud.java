package game;

import engine.render.SpriteSheet;
import engine.update.Updateable;
import java.util.Random;

public class Cloud extends SpriteSheet implements Updateable {

    float speed;

    public Cloud(int x, int y) {
        super("clouds.png", 45, 15, x, y);

        // Pick a random cloud sprite
        Random rand = new Random();
        int sprite_index = rand.nextInt(3);

        // Set the sprite
        setFrame(sprite_index);

        setOrigin(0, 0);

        setScale(4);

        speed = 0.5f + rand.nextFloat() * 2;

        registerUpdate();
    }

    @Override
    public void update() {
        // move(speed, 0);
    }
        

    @Override
    public DepthLayer getDepth() {
        return DepthLayer.BACKGROUND;
    }


}
