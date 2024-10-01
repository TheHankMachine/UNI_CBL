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

    public static void render(Graphics g){
        // TODO: clean up this shit.
        float w = instance.getWidth();
        float h = w * Config.ASPECT_RATIO;

        if(h > instance.getHeight()){
            h = instance.getHeight();
            w = h / Config.ASPECT_RATIO;
        }

        float scaleX = (float) w / Config.WIDTH;
        float scaleY = (float) h / Config.HEIGHT;

        Graphics2D g2d = (Graphics2D) g;

        g2d.translate((instance.getWidth() - w) / 2, (instance.getHeight() - h) / 2);

        AffineTransform t = AffineTransform.getScaleInstance(scaleX, scaleY);
        g2d.transform(t);

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

//        setExtendedState(JFrame.MAXIMIZED_BOTH);

        screen = new Screen();
        screen.setLayout(new BorderLayout());

        getContentPane().add(screen);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        setSize(
            500, 500
        );

//        pack();

//        setResizable(false);

        setVisible(true);

        instance = this;
    }

    public static void redraw(){
        instance.repaint();
    }

    private static class Screen extends JPanel{
        public void paintComponent(Graphics g){
            render(g);
        }
    }

}