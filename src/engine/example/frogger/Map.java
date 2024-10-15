package engine.example.frogger;

import engine.render.Renderable;

import java.awt.*;
import java.util.ArrayList;

public class Map {

    public ArrayList<Layer> layers = new ArrayList<>();

    public Map(){
        for(int i = 0; i < Frogger.GRID_HEIGHT; i++){
            layers.add(
                new Layer(i)
            );
        }
    }
}
