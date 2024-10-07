package engine.defaults;

import engine.Game;
import engine.math.Vector2D;
import engine.render.Sprite;
import engine.update.Updateable;

public class BoundryBreaker implements Updateable {

    private final Vector2D position = new Vector2D();
    private final Vector2D velocity = new Vector2D();

    public BoundryBreaker(){
        Game.getInstance().getDisplay().setDisplayOriginReference(position);

        registerUpdate();
    }

    public void update(){
        float acceleration = 1.5f;
        float speedDecay = 0.75f;

        Vector2D v = Game.getInstance().getInput().getInputVector();
        v.scale(acceleration);

        velocity.add(v);
        velocity.scale(speedDecay);

        position.add(velocity);
    }
}
