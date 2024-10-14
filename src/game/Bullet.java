package game;

import engine.math.Vector2D;
import engine.render.DisplayObject;
import engine.update.Updateable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Bullet extends DisplayObject implements Updateable {

    int x;
    int y;
    float angle;

    Vector2D velocity;
    float speed = 300f;

    public Bullet(int x, int y, float angle) {
        super();
        this.x = x;
        this.y = y;
        this.angle = angle;


        velocity = new Vector2D(
            (float) Math.sin(angle),
            (float) -Math.cos(angle));

        velocity.scale(speed);

        registerUpdate();
    }

    @Override
    public void draw(Graphics2D g) {
        draw(g, x, y, 50, 5);
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h) {
    // Save the original transform
    AffineTransform oldTransform = g.getTransform();

    // Translate to the bullet's position plus half its width and height to center the rotation
    g.translate(x + w / 2, y + h / 2);

    // Rotate the graphics context by the angle
    g.rotate(angle);

    // Draw the bullet centered at the origin
    g.setColor(Color.RED);
    g.fillRect(-w / 2, -h / 2, w, h);

    // Restore the original transform
    g.setTransform(oldTransform);
        
    }

    @Override
    public void update() {
        System.out.println("pos: " + position + "  v: " + velocity);
        move(velocity);
    }
}
