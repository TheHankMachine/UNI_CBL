package engine.examples;

import engine.Game;
import engine.render.Sprite;
import engine.update.Updateable;

public class CursorFollower extends Sprite implements Updateable {

    public CursorFollower(){
        super("defaults/smile.png");

        registerUpdate();
    }

    @Override
    public void update() {
        setPosition(Game.getInstance().getInput().getMouseLocation());
    }
}
