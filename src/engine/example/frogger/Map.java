package engine.example.frogger;

import java.util.ArrayList;

public class Map {

    public ArrayList<Layer> layers = new ArrayList<>();
    public int indexOffset = 0;

    public Map(){
        for(int i = 0; i < Frogger.GRID_HEIGHT; i++){
            layers.add(
                new Layer(i)
            );
        }
    }

    public Layer getLayer(float y){
        int index = (int) (y / Frogger.GRID_SIZE) + indexOffset;

        if(index - Frogger.GRID_BUFFER < 0){
            layers.addFirst(
                new Layer(index - indexOffset - Frogger.GRID_BUFFER)
            );

            indexOffset++;
            index++;
        }

        return layers.get(index);
    }
}
