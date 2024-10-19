package game;

import engine.Game;
import engine.render.SpriteSheet;

public class EndScreen{

    public EndScreen() {
        SpriteSheet endText = new SpriteSheet("clouds.png", 45, 15,
        Game.getInstance().getDisplayWidth() / 2,
        Game.getInstance().getDisplayHeight() / 2) {
            @Override
            public DepthLayer getDepth() {
                return DepthLayer.UI;
            }
        };
        
        endText.setFrame(3);
        endText.setScale(8);
    }
}
