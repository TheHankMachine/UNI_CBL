package engine.input;

import engine.Game;
import engine.math.Collideable;
import engine.math.Vector2D;
import engine.render.Renderable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ClickListener extends MouseAdapter {
    
    private static final List<Clickable> clickableList = new ArrayList<>();

    /**
     * Is called when JFrame receives a mouse click event. Checks
     * all elements of clickableList to check for intersection
     * with the mouse. The function returns after the first intersection
     * is found
     */
    public void mouseClicked(MouseEvent e){
        Vector2D clickGameRelative = Game.getInstance().getInput().getMousePositionGameRelative();
        Vector2D clickScreenRelative = Game.getInstance().getInput().getMousePositionScreenRelative();

        for(Clickable clickable : clickableList){
            if(clickable.getDepth() == Renderable.DepthLayer.UI){
                if(clickable.getBoundingBox().contains(clickScreenRelative)) {
                    clickable.onClick(e);
                    return;
                }
            }else{
                if(clickable.getBoundingBox().contains(clickGameRelative)) {
                    clickable.onClick(e);
                    return;
                }
            }
        }
    }

    /**
     * Adds this object to the clickable list. The most recent
     * object added will have priority.
     */
    public static void addClickListenerTo(Clickable object){
        clickableList.addFirst(object);
    }

    /**
     * Removes the object from the clickable list.
     */
    public static void removeClickListenerOf(Clickable object){
        clickableList.remove(object);
    }
    
}