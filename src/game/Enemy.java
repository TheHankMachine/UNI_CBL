package game;

import engine.render.SpriteSheet;
import engine.update.Updateable;

public class Enemy extends SpriteSheet implements Updateable {

    Player player;

    public Enemy(int x, int y, String spriteSheetName, Player player) {
        super(spriteSheetName, 16, 16, x, y);

        this.player = player;  

        registerUpdate();
    }

    @Override
    public void update() {

    }

    @Override
    public DepthLayer getDepth() {
        return DepthLayer.FOREGROUND;
    }
}
