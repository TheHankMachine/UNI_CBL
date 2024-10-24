package game;

import engine.Game;
import engine.render.DisplayObject;
import engine.render.SpriteFont;
import engine.render.SpriteSheet;
import java.awt.event.MouseEvent;

public class EndScreen {

    private DisplayObject endText;
    private final Button newGameButton;
    private final Button quitButton;

    public EndScreen(PlaneArcade game) {
        float screenWidth = Game.getInstance().getDisplayWidth();
        float screenHeight = Game.getInstance().getDisplayHeight();

        endText = new Text("You Died",
                screenWidth / 2,
                screenHeight * 0.35f) {

            @Override
            public DepthLayer getDepth() {
                return DepthLayer.UI;
            }
        };

        newGameButton = new Button("NEW GAME", screenWidth / 2, screenHeight / 2) {
            @Override
            public void onClick(MouseEvent e) {
                game.play();
            }

            // Make the button wider to fit the text
            @Override
            public float getWidth() {
                return super.getWidth() * 1.5f;
            }
        };

        quitButton = new Button("QUIT", screenWidth / 2, screenHeight * 0.6f) {
            @Override
            public void onClick(MouseEvent e) {
                System.exit(0);
            }
        };

        endText.setScale(8);
    }

    public void close() {
        endText.deregisterRender();

        newGameButton.deregisterRender();
        newGameButton.removeClickListener();

        quitButton.deregisterRender();
        quitButton.removeClickListener();
    }
}
