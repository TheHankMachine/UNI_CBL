package engine.render;

import java.awt.*;
import java.awt.image.BufferedImage;

import engine.Game;
import engine.math.Axis2D;
import engine.math.Vector2D;

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

//    void move(Vector2D vector);
//
//    void setPosition(Vector2D to);
//
//    void setPosition(float x, float y);
//
//    void setPositionRef(Vector2D ref);
//
//    void setVisible(boolean visible);
//
//    void setScale(float scale);
//
//    float getScale();
//
//    float getWidth();
//
//    float getHeight();
//
//    void setFlip(boolean flipX, boolean flipY);
//
//    void setFlipX(boolean flipX);
//
//    void setFlipY(boolean flipY);
//
//    void setOrigin(float ox, float oy);

}