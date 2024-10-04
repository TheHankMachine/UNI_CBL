package engine.render;

import engine.Game;
import engine.math.Axis2D;
import engine.math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite implements Renderable {
    private final BufferedImage image;

    private Vector2D position = new Vector2D();

    private boolean flipX = false;
    private boolean flipY = false;
    private boolean visible = true;

    private float originX = 0.5f;
    private float originY = 0.5f;

    protected float scale = 1;
    protected int width;
    protected int height;

    protected Sprite(String asset){
        image = loadImage(asset);

        width = image.getWidth();
        height = image.getHeight();

        registerRender();
    }

    public Sprite(String asset, float x, float y){
        this(asset);
        setPosition(x, y);
    }

    public Sprite(String asset, Vector2D position){
        this(asset);
        setPosition(position);
    }

    public void move(Vector2D vector){
        position.add(vector);
    }

    /**
     * Sets the position of the sprite.
     *
     * @param to Vector2D object which the
     *          position is set to
     */
    public void setPosition(Vector2D to){
        position.setTo(to);
    }

    /**
     * Sets the position of the sprite.
     *
     * @param x the x position for the sprite
     * @param y the y position for the sprite
     */
    public void setPosition(float x, float y){
        position.setTo(x, y);
    }

    /**
     * Sets the position vector to point to another
     * vector.
     *
     * @param ref The vector object which position
     *            will now point to
     */
    public void setPositionRef(Vector2D ref){
        position = ref;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public float getScale(){
        return scale;
    }

    /**
     * @return the width of the sprite after scaling
     */
    public float getWidth(){
        return width * scale;
    }

    /**
     * @return the width of the sprite after scaling
     */
    public float getHeight(){
        return height * scale;
    }

    public void setFlip(boolean flipX, boolean flipY){
        setFlipX(flipX);
        setFlipY(flipY);
    }

    public void setFlipX(boolean flipX){
        this.flipX = flipX;
    }

    public void setFlipY(boolean flipY){
        this.flipY = flipY;
    }

    public void setOrigin(float ox, float oy){
        this.originX = ox;
        this.originY = oy;
    }

    @Override
    public void draw(Graphics2D g) {
        if(!visible) return;

        int x = position.get(Axis2D.X).intValue();
        int y = position.get(Axis2D.Y).intValue();

        float ox = originX;
        float oy = originY;

        int w = (int) getWidth();
        if(flipX) {
            w = -w;
            ox = 1 - ox;
        }
        x -= (int) (w * ox);

        int h = (int) getHeight();
        if(flipY) {
            h = -h;
            oy = 1 - oy;
        }
        y -= (int) (h * oy);

        drawImage(g, image, x, y, w, h);
    }

    // exists so that spritesheet can override just this
    public void drawImage(Graphics2D g, BufferedImage image, int x, int y, int w, int h){
        g.drawImage(image, x, y, w, h, null);
    }

    public static BufferedImage loadImage(String assetName){
        return Game.getInstance().loadImage(assetName);
    }
}
