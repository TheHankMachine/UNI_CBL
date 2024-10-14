package engine.update;

import java.util.ArrayList;

public interface Updateable {

    ArrayList<Updateable> updateList = new ArrayList<>();

    /**
     * Adds object to the updatelist. On all subsequent ticks
     * the update method will be called
     */
    default void registerUpdate(){
        updateList.add(this);
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