package game;

import engine.math.Axis2D;
import engine.math.Collideable;
import engine.math.Vector2D;
import engine.render.DisplayObject;
import engine.update.Updateable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Bullet extends DisplayObject implements Updateable {

    private final Vector2D velocity;
    private final float speed = 30f;

    private final ArrayList<Enemy> enemies;

    public Bullet(int x, int y, Vector2D velocity, PlaneArcade game) {
        super();
        this.velocity = velocity.copy();

        this.width = 2;
        this.height = 2;

        this.velocity.normalise();
        this.velocity.scale(speed);

        this.enemies = game.getEnemies();

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
    }

    private void checkCollision(Enemy enemy) {
        if (Collideable.collides(this, enemy)) {
            enemy.die();
            die();
        }
    }

    private void die() {
        deregisterRender();
        deregisterUpdate();
    }

    @Override
    public void update() {
        // Dear Kuba,

        // You can't do this:
        // You are removing elements from the list enemies
        // while iterating through it. This throws a concurrent
        // modification exception. This will be thrown in a
        // enhanced for loop or a for each loop when a modification
        // happens while iterating through it.
        // sorry for the inconvenience, but please change this
        enemies.forEach((enemy) -> checkCollision(enemy));
        move(velocity);

        // this worked before because the part of code
        // that ran the update method had a try catch.
        // that was removed. Thus, this is now your problem.
        // Sincerely,
        // - Hank
    }
}
