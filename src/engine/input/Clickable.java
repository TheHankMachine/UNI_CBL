package engine.input;

import engine.math.Collideable;

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
}