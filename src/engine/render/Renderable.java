package engine.render;

import java.awt.*;
import engine.Game;

public interface Renderable {

    enum DepthLayer {
        BACKGROUND,
        MIDDLEGROUND,
        FOREGROUND,
        UI
    }

    default void registerRender(){
        Game.getInstance().getRenderer().add(this);
    }

    void draw(Graphics2D g);

    default DepthLayer getDepth(){
        return DepthLayer.MIDDLEGROUND;
    };

}