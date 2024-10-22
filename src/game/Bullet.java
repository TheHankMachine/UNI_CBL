package game;

import engine.Game;
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
    private boolean shotByPlayer;

    private final ArrayList<Enemy> enemies;
    private final Player player;

    public Bullet(int x, int y, Vector2D velocity, PlaneArcade game, boolean shotByPlayer) {
        super();
        this.velocity = velocity.copy();
        this.shotByPlayer = shotByPlayer;

        this.width = 2;
        this.height = 2;

        this.velocity.normalise();
        this.velocity.scale(speed);

        this.enemies = game.getEnemies();
        this.player = game.getPlayer();

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

    private void cleanBullets() {
        int screenWidth = Game.getInstance().getDisplayWidth();
        int screenHeight = Game.getInstance().getDisplayHeight();

        if (getX() > player.getX() + screenWidth / 2 + getWidth() ||
            getX() < player.getX() - screenWidth / 2 - getWidth() ||
            getY() > player.getY() + screenHeight / 2 + getHeight() ||
            getY() < player.getY() - screenHeight / 2 - getHeight()) {
            deregisterRender();
            deregisterUpdate();
        }
    }

    private void checkCollision(Enemy enemy) {
        if (shotByPlayer && Collideable.collides(this, enemy)) {
            if (!enemy.isHittable()) {
                return;
            }
            
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
        enemies.forEach((enemy) -> checkCollision(enemy));
        move(velocity);
        cleanBullets();
    }
}
