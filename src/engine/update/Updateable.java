package engine.update;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface Updateable {

    List<Updateable> updateList = new LinkedList<>();

    List<Updateable> removeQueue = new LinkedList<>();
    List<Updateable> addQueue = new LinkedList<>();

    /**
     * Adds object to the updatelist. On all subsequent ticks
     * the update method will be called
     */
    default void registerUpdate(){
        addQueue.add(this);
    }

    default void deregisterUpdate(){
        removeQueue.add(this);
    }

    /**
     * Called automatically by the game every tick
     */
    void update();

    /**
     * Calls update on all elements in the updateList
     */
    static void updateAll(){
        updateList.addAll(addQueue);
        addQueue.clear();

        for(Updateable updateable : updateList) {
            updateable.update();
        }

        updateList.removeAll(removeQueue);
        removeQueue.clear();
    }
}