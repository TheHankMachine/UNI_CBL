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

    public Screen(GameConfig config, Display display){
        this.display = display;
        this.config = config;
    }

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

    public void paintComponent(Graphics g){
        long startTime = System.nanoTime();

        draw((Graphics2D) g);

        Game.debug_renderTimeMs = (int) (System.nanoTime() - startTime) / 1_000_000;
    }

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

    public void draw(Graphics2D g2d){
        EnumMap<Renderable.DepthLayer, ArrayList<Renderable>> renderList = display.getRenderList();
        g2d.translate(offsetX, offsetY);
        g2d.transform(transform);

        g2d.setColor(config.backgroundColor);
        g2d.fillRect(0, 0, config.width, config.height);
        g2d.clipRect(0, 0, config.width, config.height);

        float dox = display.getDisplayOriginX();
        float doy = display.getDisplayOriginY();

        g2d.translate(-dox, -doy);

        for(Renderable.DepthLayer layer : Renderable.DepthLayer.values()){
            if(layer == Renderable.DepthLayer.UI){
                g2d.translate(dox, doy);
            }
            for(Renderable renderable : renderList.get(layer)){
                renderable.draw(g2d);
            }
        }
    }
}
