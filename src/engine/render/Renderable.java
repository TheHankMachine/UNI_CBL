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

    /**
     * Adds object to the renderlist
     */
    default void registerRender(){
        Game.getInstance().getDisplay().add(this);
    }

    /**
     * Draws the object to the screen
     */
    void draw(Graphics2D g);

    /**
     * @return the depth layer. This is related to the
     * depth sorting of all objects on the screen. There are
     * fixed number of depth layers to simply this process.
     */
    default DepthLayer getDepth(){
        return DepthLayer.MIDDLEGROUND;
    };

}