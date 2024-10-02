package render;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.EnumMap;

import game.Config;
import render.Renderable.DepthLayer;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JFrame {

    public static Renderer instance;

    public static final EnumMap<DepthLayer, ArrayList<Renderable>> renderList;

    static{
        renderList = new EnumMap<>(DepthLayer.class);

        for(DepthLayer layer : DepthLayer.values()){
            renderList.put(layer, new ArrayList<>());
        }
    }

    public static void add(Renderable e){
        renderList.get(e.getDepth()).add(e);
    }

    public static int width(){
        return instance.screen.getWidth();
    }

    public static int height(){
        return instance.screen.getHeight();
    }

    private static float graphicsScaleX, graphicsScaleY;
    private static int graphicsOffsetX, graphicsOffsetY;

    public static void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(graphicsOffsetX, graphicsOffsetY);
        AffineTransform t = AffineTransform.getScaleInstance(graphicsScaleX, graphicsScaleY);
        g2d.transform(t);

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);

        for(DepthLayer layer : DepthLayer.values()){
            for(Renderable renderable : renderList.get(layer)){
                renderable.draw(g2d);
            }
        }
    }

    private Screen screen;

    public Renderer(){
        super("Frame");

        screen = new Screen();
        screen.setLayout(new BorderLayout());

        getContentPane().add(screen);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(600, 400);

        addComponentListener(new ResizeHandler());

        setVisible(true);

        instance = this;
    }

    public static void redraw(){
        instance.repaint();
    }

    private static class Screen extends JPanel{
        public void paintComponent(Graphics g){
            long startTime = System.nanoTime();

            render(g);

            g.setColor(Color.blue);

            g.drawString(String.format("render time: %dms",
                (System.nanoTime() - startTime) / 1_000_000
            ),10, 10);
        }
    }

    private static class ResizeHandler extends ComponentAdapter{
        public void componentResized(ComponentEvent evt) {
            Component c = (Component) evt.getSource();

            int width = c.getWidth();
            int height = c.getHeight();

            int frameWidth, frameHeight;

            frameWidth = width;
            frameHeight = (int) (frameWidth * Config.ASPECT_RATIO);

            if(frameHeight > height){
                frameHeight = height;
                frameWidth = (int) (frameHeight / Config.ASPECT_RATIO);
            }

            graphicsScaleX = (float) frameWidth / Config.WIDTH;
            graphicsScaleY = (float) frameHeight / Config.HEIGHT;

            graphicsOffsetX = (width - frameWidth) / 2;
            graphicsOffsetY = (height - frameHeight) / 2;
        }
    }

}