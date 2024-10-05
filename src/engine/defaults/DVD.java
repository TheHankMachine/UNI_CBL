package engine.defaults;

import engine.Game;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.Sprite;
import engine.render.SpriteSheet;
import engine.update.Updateable;

public class DVD extends SpriteSheet implements Updateable {

    Vector2D v = new Vector2D(1, 0);

    public DVD(){
        super("dvd.png", 15, 12,
            (float) (Math.random() * (Game.getInstance().getWidth() - 15)),
            (float) (Math.random() * (Game.getInstance().getHeight() - 12))
        );

        setOrigin(0, 0);

        setFrame((int) (Math.random() * 4));
        v.rotate((float) (Math.random() * 2 * Math.PI));

        registerUpdate();
    }

    @Override
    public void update() {
        move(v);

        if(getX() < 0 || getX() + getWidth() > Game.getInstance().getWidth()){
            v.flip(Axis2D.X);
        }

        if(getY() < 0 || getY() + getHeight() > Game.getInstance().getHeight()){
            v.flip(Axis2D.Y);
        }
    }
}
