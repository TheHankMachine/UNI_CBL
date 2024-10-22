package engine.input;

import engine.math.Collideable;
import engine.render.Renderable;

import java.awt.event.MouseEvent;

public interface Clickable extends Collideable {

    /**
     * Called when the object is clicked
     */
    void onClick(MouseEvent e);

    /**
     * Registers this object in ClickListener. After registration
     * the onClick method will be called whenever the mouse
     * clicks and its position intersects with this object's
     * bounding box.
     */
    default void addClickListener(){
        ClickListener.addClickListenerTo(this);
    }

    default void removeClickListener(){
        ClickListener.removeClickListenerOf(this);
    }


    /**
     * @return the depth layer. This is related to the
     * depth sorting of all objects on the screen. There are
     * fixed number of depth layers to simplify this process.
     */
    default Renderable.DepthLayer getDepth(){
        return Renderable.DepthLayer.MIDDLEGROUND;
    };
}