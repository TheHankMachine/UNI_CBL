package engine.render.display;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.EnumMap;

import engine.GameConfig;
import engine.math.Vector2D;
import engine.render.Renderable;
import engine.render.Renderable.DepthLayer;

public class Display extends JFrame {

    private final EnumMap<DepthLayer, ArrayList<Renderable>> renderList;
    private final GameConfig config;
    private final Screen screen;

    public Display(GameConfig config){
        this.config = config;

        screen = new Screen(config, this);

        renderList = new EnumMap<>(DepthLayer.class);
        for(DepthLayer layer : DepthLayer.values()){
            renderList.put(layer, new ArrayList<>());
        }

        setBackground(Color.BLACK);
        add(screen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentListener(new ResizeHandler());
        setVisible(true);
        setSize(config.width, config.height);
    }

    public void add(Renderable e){
        renderList.get(e.getDepth()).add(e);
    }

    public EnumMap<DepthLayer, ArrayList<Renderable>> getRenderList(){
        return renderList;
    }

    public Screen getScreen(){
        return screen;
    }

    /**
     * Sets the position of the display origin
     */
    public void setDisplayOrigin(float x, float y){
        screen.displayOrigin.setTo(x, y);
    }

    public void setDisplayOrigin(Vector2D to){
        screen.displayOrigin.setTo(to);
    }

    /**
     * Sets the display origin to reference
     * another vector
     */
    public void setDisplayOriginReference(Vector2D ref){
        screen.displayOrigin = ref;
    }

    private class ResizeHandler extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            screen.onResize();
        }
    }

}