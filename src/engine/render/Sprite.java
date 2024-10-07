package engine.render;

import engine.Game;
import engine.math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite extends DisplayObject {
    protected final BufferedImage image;

    protected Sprite(String fileName){
        image = loadImage(fileName);

        width = image.getWidth();
        height = image.getHeight();
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

    //Exists to be overridden by SpriteSheet
    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h){
        g.drawImage(image, x, y, w, h, null);
    }

    public static BufferedImage loadImage(String fileName){
        return Game.getInstance().loadImage(fileName);
    }
}
