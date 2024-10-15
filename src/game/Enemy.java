package game;

import engine.update.Updateable;

public class Enemy extends Ship implements Updateable {

    private final Player player;
    // private Vector2D directionVector;

    public Enemy(int x, int y, String spriteSheetName, Player player) {
        super(spriteSheetName, x, y, 6f);

        this.player = player;  

        registerUpdate();
    }

    @Override
    public void update() {
        rotateToVector(player.getOldPosition());
        move();
    }
}
