package engine.example;

import engine.Game;
import engine.render.Sprite;
import engine.update.Updateable;

public class CursorFollower extends Sprite implements Updateable {

    public CursorFollower(){
        super("example/smile.png");

        registerUpdate();
    }

    @Override
    public void update() {
        setPosition(Game.getInstance().getInput().getMousePositionGameRelative());
    }
}
