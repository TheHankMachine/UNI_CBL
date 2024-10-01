package render;

import java.awt.*;

public interface Renderable {

    enum DepthLayer {
        BACKGROUND,
        MIDDLEGROUND,
        FOREGROUND,
        UI
    }

    default void registerRender(){
        Renderer.add(this);
    }

    void draw(Graphics2D g);

    default DepthLayer getDepth(){
        return DepthLayer.MIDDLEGROUND;
    };

}