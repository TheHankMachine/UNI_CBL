package engine.render;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.EnumMap;

import engine.GameConfig;
import engine.render.Renderable.DepthLayer;

import javax.swing.*;
import java.awt.*;

//TODO: clean up needed very badly
public class Renderer extends JFrame {

    private final EnumMap<DepthLayer, ArrayList<Renderable>> renderList;
    private final GameConfig config;

//    private float graphicsScaleX, graphicsScaleY;
    private float graphicsScale;

    private int graphicsOffsetX, graphicsOffsetY;

    private Screen screen;

    public Renderer(GameConfig config){
        super("Frame");

        this.config = config;

        renderList = new EnumMap<>(DepthLayer.class);
        for(DepthLayer layer : DepthLayer.values()){
            renderList.put(layer, new ArrayList<>());
        }

        screen = new Screen();
        screen.setLayout(new BorderLayout());

        //TODO: remove me
        getContentPane().add(screen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        addComponentListener(new ResizeHandler());
        setVisible(true);
    }

    public void add(Renderable e){
        renderList.get(e.getDepth()).add(e);
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(graphicsOffsetX, graphicsOffsetY);
        AffineTransform t = AffineTransform.getScaleInstance(graphicsScale, graphicsScale);
        g2d.transform(t);

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, config.width, config.height);

        for(DepthLayer layer : DepthLayer.values()){
            for(Renderable renderable : renderList.get(layer)){
                renderable.draw(g2d);
            }
        }
    }

    private class Screen extends JPanel{
        public void paintComponent(Graphics g){
            long startTime = System.nanoTime();

            render(g);

            g.setColor(Color.blue);

            g.drawString(String.format("engine.render time: %dms",
                (System.nanoTime() - startTime) / 1_000_000
            ),10, 10);
        }
    }

    private class ResizeHandler extends ComponentAdapter{
        public void componentResized(ComponentEvent evt) {
            Component c = (Component) evt.getSource();

            int width = screen.getWidth();
            int height = screen.getHeight();

            int frameWidth, frameHeight;

            frameHeight = height;
            frameWidth = (int) (height * config.aspectRatio);

            if(frameWidth > width){
                frameWidth = width;
                frameHeight = (int) (width / config.aspectRatio);
            }

            graphicsScale = (float) frameHeight / config.height;

            graphicsOffsetX = (width - frameWidth) / 2;
            graphicsOffsetY = (height - frameHeight) / 2;
        }
    }

}