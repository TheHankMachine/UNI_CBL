package engine.render;

import engine.Game;

import java.awt.*;

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