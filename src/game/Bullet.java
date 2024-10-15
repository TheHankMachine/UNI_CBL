package game;

import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.DisplayObject;
import engine.update.Updateable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet extends DisplayObject implements Updateable {

    int x;
    int y;

    Vector2D velocity;
    float speed = 300f;

    public Bullet(int x, int y, Vector2D velocity) {
        super();
        this.x = x;
        this.y = y;
        this.velocity = velocity.copy();

        this.velocity.normalise();
        this.velocity.scale(30);

        registerUpdate();
    }

    @Override
    public void draw(Graphics2D g) {
        draw(g, x, y, 50, 5);
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h) {
        // Draw the bullet centered at the origin  
        int endX = x + velocity.get(Axis2D.X).intValue();
        int endY = y + velocity.get(Axis2D.Y).intValue();

        g.setColor(Color.YELLOW);
        g.setStroke(new BasicStroke(9));
        g.drawLine(x, y, endX, endY);

        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(6));
        g.drawLine(x, y, endX, endY);
    }

    @Override
    public void update() {
        System.out.println("pos: " + position + "  v: " + velocity);
        // move(velocity);
    }
}
