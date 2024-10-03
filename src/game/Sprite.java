package game;

import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.Renderable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite implements Renderable {

    private final BufferedImage image;

    private Vector2D position = new Vector2D(30, 30);

    private boolean flipX = false;
    private boolean flipY = false;
    private boolean visible = true;

    protected float scale = 1;
    protected int width;
    protected int height;

    public Sprite(String asset){
        image = loadImage(asset);

        width = image.getWidth();
        height = image.getHeight();

        registerRender();
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

    @Override
    public void draw(Graphics2D g) {
        if(!visible) return;

        int x = position.get(Axis2D.X).intValue();
        int y = position.get(Axis2D.Y).intValue();

        int w = (int) getWidth();
        if(flipX) w = -w;
        x -= w / 2;

        int h = (int) getHeight();
        if(flipY) h = -h;
        y -= h / 2;

        drawImage(g, image, x, y, w, h);
    }

    // exists so that spritesheet can override just this
    public void drawImage(Graphics2D g, BufferedImage image, int x, int y, int w, int h){
        g.drawImage(image, x, y, w, h, null);
    }

    public static BufferedImage loadImage(String assetName){
        File file = new File(Config.ASSET_FILE_PATH + assetName);

        try{
             return ImageIO.read(file);
        }catch(IOException e){
            throw new RuntimeException(String.format("File not found:\n%s", file.getAbsolutePath()));
        }
    }

}
