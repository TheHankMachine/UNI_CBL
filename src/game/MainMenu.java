package game;

import engine.Game;
import java.awt.event.MouseEvent;

public class MainMenu {

    private final Text planeArcadeText;
    private final Button playButton;
    private final Button quitButton;

    public MainMenu(PlaneArcade game) {
        float screenWidth = Game.getInstance().getDisplayWidth();
        float screenHeight = Game.getInstance().getDisplayHeight();

        planeArcadeText = new Text("PLANE ARCADE", screenWidth / 2, screenHeight * 0.35f);

        playButton = new Button("PLAY", screenWidth / 2, screenHeight / 2) {
            @Override
            public void onClick(MouseEvent e) {
                game.paly();
            }
        };
        
        quitButton = new Button("QUIT", screenWidth / 2, screenHeight * 0.6f) {
            @Override
            public void onClick(MouseEvent e) {
                System.exit(0);
            }
        };
    }

    public void close() {
        planeArcadeText.deregisterRender();
        playButton.deregisterRender();
        quitButton.deregisterRender();
    }
}
