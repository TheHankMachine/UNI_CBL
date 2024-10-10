package engine.example;

import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.DisplayObject;

import java.awt.*;

public class DisplayVector extends DisplayObject {

    private final Vector2D origin, vector;

    public DisplayVector(Vector2D origin, Vector2D vector){
        super();

        setScale(10);

        this.vector = vector;
        this.origin = origin;
    }

    @Override
    public void draw(Graphics2D g){
        int x = origin.get(Axis2D.X).intValue();
        int y = origin.get(Axis2D.Y).intValue();

        float length = vector.getLength();

        int dx = (int) (vector.get(Axis2D.X) * scale / length);
        int dy = (int) (vector.get(Axis2D.Y) * scale / length);

        draw(g, x, y,x + dx, y + dy);
    }

    @Override
    protected void draw(Graphics2D g, int x, int y, int w, int h) {
        g.setColor(Color.BLACK);
        g.drawLine(x, y, w, h);
    }
}
