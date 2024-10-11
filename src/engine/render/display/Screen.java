package engine.render.display;

import engine.Game;
import engine.GameConfig;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.Renderable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.EnumMap;

public class Screen extends JPanel {

    private final GameConfig config;
    private final Display display;

    private int offsetX, offsetY;
    private float scale;
    private AffineTransform transform;

    private Color backgroundColor;

    public Screen(GameConfig config, Display display){
        this.display = display;
        this.config = config;

        this.backgroundColor = config.backgroundColor;
    }

    /**
     * @return the current background colour of the screen
     */
    public Color getBackgroundColor(){
        return backgroundColor;
    }

    /**
     * Sets the background colour to param color
     */
    public void setBackgroundColor(Color color){
        backgroundColor = color;
    }

    /**
     * @return the position of the point represented by params x and y
     * relative to the screen.
     */
    public Vector2D getLocalPositionFromGlobal(float x, float y){
        // Dear David,
        //
        // I am sorry about these magic numbers.
        // I am not happy about it either.
        // But this appears to be the only way.
        //
        // Please forgive me.
        //
        // Sincerely,
        //  - Hank

        x -= 7;
        y -= 30;

        return new Vector2D(
            (x - offsetX) / scale,
            (y - offsetY) / scale
        );
    }

    /**
     * Starts the chain of functions that draws all elements of renderlist
     * to the screen
     */
    public void paintComponent(Graphics g){
        long startTime = System.nanoTime();

        draw((Graphics2D) g);

        Game.debug_renderTimeMs = (int) (System.nanoTime() - startTime) / 1_000_000;
    }

    /**
     * Called on resizing the frame. Sets the transformation and offset
     * to keep the screen to a constant aspect ratio centered in the middle of the
     * panel.
     */
    public void onResize() {
        int frameWidth, frameHeight;
        int width = getWidth();
        int height = getHeight();

        frameHeight = height;
        frameWidth = (int) (height * config.aspectRatio);

        if (frameWidth > width) {
            frameWidth = width;
            frameHeight = (int) (width / config.aspectRatio);
        }

        scale = (float) frameHeight / config.height;
        transform = AffineTransform.getScaleInstance(scale, scale);

        offsetX = (width - frameWidth) / 2;
        offsetY = (height - frameHeight) / 2;
    }

    /**
     * Draws all renderable objects in the renderlist to the screen.
     */
    public void draw(Graphics2D g2d){
        EnumMap<Renderable.DepthLayer, ArrayList<Renderable>> renderList = display.getRenderList();
        g2d.translate(offsetX, offsetY);
        g2d.transform(transform);

        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, config.width, config.height);
        g2d.clipRect(0, 0, config.width, config.height);

        float dox = display.getDisplayOriginX();
        float doy = display.getDisplayOriginY();

        g2d.translate(-dox, -doy);

        for(Renderable.DepthLayer layer : Renderable.DepthLayer.values()){
            //TODO: find better way of handling this
            //UI should ignore display offset
            if(layer == Renderable.DepthLayer.UI){
                g2d.translate(dox, doy);
            }
            for(Renderable renderable : renderList.get(layer)){
                renderable.draw(g2d);
            }
        }
    }
}
