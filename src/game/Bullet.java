package game;

import engine.math.Axis2D;
import engine.math.Collideable;
import engine.math.Vector2D;
import engine.render.DisplayObject;
import engine.update.Updateable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet extends DisplayObject implements Updateable {

    Vector2D velocity;
    float speed = 30f;

    public Bullet(int x, int y, Vector2D velocity) {
        super();
        this.velocity = velocity.copy();

        this.width = 2;
        this.height = 2;

        this.velocity.normalise();
        this.velocity.scale(speed);

        setPosition(x, y);

        registerUpdate();
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h) {
        // Draw the bullet centered at the origin
        int endX = x + velocity.get(Axis2D.X).intValue();
        int endY = y + velocity.get(Axis2D.Y).intValue();

        g.setColor(new Color(0xfaa21b));
        g.setStroke(new BasicStroke(9));
        g.drawLine(x, y, endX, endY);

        g.setColor(new Color(0xfff1e9));
        g.setStroke(new BasicStroke(6));
        g.drawLine(x, y, endX, endY);

        // Alternatively, for more pixelated bullets,
        // you could do something like this
//        g.setColor(new Color(0xf7ec2f));
//        g.fillRect(x, y, w, h);
    }

    @Override
    public void update() {
        move(velocity);
    }
}
