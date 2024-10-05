package engine.update;

import java.util.ArrayList;

public interface Updateable {

    ArrayList<Updateable> updateList = new ArrayList<>();

    default void registerUpdate(){
        updateList.add(this);
    }

    void update();

    static void updateAll(){
        updateList.forEach(Updateable::update);
    }

}