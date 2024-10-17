package game;

import engine.render.SpriteSheet;
import engine.update.Updateable;

public class Explosion extends SpriteSheet implements Updateable{

    private final int framesToChangeSprite = 12;
    private int frameCounter = 0;
    private int currentSprite = 0;

    public Explosion(int x, int y) {
        super("explosion.png", 16, 16, x, y);

        registerUpdate();
    }

    @Override
    public void update() {
        frameCounter++;

        if (frameCounter == framesToChangeSprite && currentSprite < 4) {
            currentSprite++;

            setFrame(currentSprite);

            frameCounter = 0;
        }

        if (currentSprite >= 4) {
            deregisterRender();
            deregisterUpdate();
        }
    }
}
