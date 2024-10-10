package engine.example;

import engine.Game;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;

public class DVD extends SpriteSheet implements Updateable {

    public Vector2D v = new Vector2D(0.2f, 0);

    public DVD(){
        super("example/dvd.png", 15, 12,
            (float) (Math.random() * (Game.getInstance().getDisplayWidth() - 15)),
            (float) (Math.random() * (Game.getInstance().getDisplayHeight() - 12))
        );

        setOrigin(0, 0);

//        setFrame((int) (Math.random() * 4));
        v.rotate((float) (Math.random() * 2 * Math.PI));

        registerUpdate();
    }

    @Override
    public void update() {
        move(v);

//        Vector2D pos = Game.getInstance().getInput().getMousePositionGameRelative();

//        if(Collideable.collides(pos, this)){
//            setFrame(1);
//        }else{
//            setFrame(0);
//        }

        if(getX() < 0 || getX() + getWidth() > Game.getInstance().getDisplayWidth()){
            v.flip(Axis2D.X);
        }

        if(getY() < 0 || getY() + getHeight() > Game.getInstance().getDisplayHeight()){
            v.flip(Axis2D.Y);
        }
    }
}
