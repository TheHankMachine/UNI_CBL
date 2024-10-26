package game;

import engine.Game;
import engine.render.DisplayObject;
import java.awt.event.MouseEvent;

public class EndScreen {

    private DisplayObject endText;
    private final Button newGameButton;
    private final Button quitButton;
    private Text scoreText;

    public EndScreen(PlaneArcade game, int score) {
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

        scoreText = new Text("Score:" + Integer.toString(score),
        screenWidth / 2,
        screenHeight * 0.45f);

        newGameButton = new Button("NEW GAME", screenWidth / 2, screenHeight * 0.55f) {
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

        quitButton = new Button("QUIT", screenWidth / 2, screenHeight * 0.65f) {
            @Override
            public void onClick(MouseEvent e) {
                System.exit(0);
            }
        };

        endText.setScale(8);
    }

    public void close() {
        endText.deregisterRender();
        scoreText.deregisterRender();

        newGameButton.deregisterRender();
        newGameButton.removeClickListener();

        quitButton.deregisterRender();
        quitButton.removeClickListener();

    }
}
