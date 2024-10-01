package render;

import java.util.ArrayList;
import java.util.EnumMap;
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
        for(DepthLayer layer : DepthLayer.values()){
            for(Renderable renderable : renderList.get(layer)){
                renderable.draw(g);
            }
        }
    }

    private Screen screen;

    public Renderer(){
        super("Frame");

        screen = new Screen();
        screen.setLayout(new GridLayout(1, 1));
        this.add(screen);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        setSize(
            500, 500
        );

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