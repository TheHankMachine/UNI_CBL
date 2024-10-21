package game;

import engine.Game;
import engine.render.SpriteSheet;
import java.awt.event.MouseEvent;

public class EndScreen {

    private SpriteSheet endText;
    private Button newGameButton;
    private final Button quitButton;

    public EndScreen(PlaneArcade game) {
        float screenWidth = Game.getInstance().getDisplayWidth();
        float screenHeight = Game.getInstance().getDisplayHeight();

        endText = new SpriteSheet("clouds.png", 45, 15,
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
                // game.paly();
                System.out.println("new game");
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
                // System.exit(0);
                System.out.println("quit");
            }
        };

        endText.setFrame(3);
        endText.setScale(8);
    }

    public void close() {
        endText.deregisterRender();
        newGameButton.deregisterRender();
        quitButton.deregisterRender();
    }
}
