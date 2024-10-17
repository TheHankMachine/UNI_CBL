package engine.update;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface Updateable {

    List<Updateable> updateList = new LinkedList<>();

    /**
     * Adds object to the updatelist. On all subsequent ticks
     * the update method will be called
     */
    default void registerUpdate(){
        updateList.add(this);
    }

    default void deregisterUpdate(){
        updateList.remove(this);
    }

    /**
     * Called automatically by the game every tick
     */
    void update();

    /**
     * Calls update on all elements in the updateList
     */
    static void updateAll(){
        for(int i = 0; i < updateList.size(); i++){
            updateList.get(i).update();
        }
    }

}