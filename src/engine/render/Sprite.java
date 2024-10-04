package engine.render;

import engine.Game;
import engine.math.Axis2D;
import engine.math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite extends DisplayObject {
    private final BufferedImage image;

    protected Sprite(String fileName){
        image = loadImage(fileName);

        width = image.getWidth();
        height = image.getHeight();

        registerRender();
    }

    /**
     * Creates new sprite at location x, y
     */
    public Sprite(String fileName, float x, float y){
        this(fileName);
        setPosition(x, y);
    }

    /**
     * Creates a new sprite at location position
     */
    public Sprite(String fileName, Vector2D position){
        this(fileName);
        setPosition(position);
    }

    @Override
    public void draw(Graphics2D g) {
        if(!visible) return;

        int x = position.get(Axis2D.X).intValue();
        int y = position.get(Axis2D.Y).intValue();

        float ox = originX;
        float oy = originY;

        int w = (int) getWidth();
        int h = (int) getHeight();

        if(flipX) {
            w = -w;
            ox = 1 - ox;
        }
        if(flipY) {
            h = -h;
            oy = 1 - oy;
        }

        x -= (int) (w * ox);
        y -= (int) (h * oy);

        drawImage(g, image, x, y, w, h);
    }

    //Exists to be overridden by SpriteSheet
    public void drawImage(Graphics2D g, BufferedImage image, int x, int y, int w, int h){
        g.drawImage(image, x, y, w, h, null);
    }

    public static BufferedImage loadImage(String fileName){
        return Game.getInstance().loadImage(fileName);
    }
}
