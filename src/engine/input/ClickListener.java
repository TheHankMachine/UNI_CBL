package engine.input;

import engine.Game;
import engine.math.Collideable;
import engine.math.Vector2D;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ClickListener extends MouseAdapter {
    
    private static final List<Clickable> clickableList = new ArrayList<>();

    /**
     * Is called when jframe receives a mouse click event. Checksw
     * all elements of clickableList to check for intersection
     * with the mouse. The function returns after the first intersection
     * is found
     */
    public void mouseClicked(MouseEvent e){
        Vector2D clickLocation = Game.getInstance().getInput().getMousePositionGameRelative();

        for(Clickable clickable : clickableList){
            if(clickable.getBoundingBox().contains(clickLocation)){
                clickable.onClick(e);
                return;
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
    
}
