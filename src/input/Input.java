package input;

import math.Vector2D;
import render.Renderer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Input {

    public enum InputKey {
        UP(87),
        LEFT(65),
        DOWN(83),
        RIGHT(68);

        public final int key;

        InputKey(int key){
            this.key = key;
        }
    }

    private static HashMap<Integer, Integer> pffft = new HashMap<>();

    public static boolean isDown(InputKey key){
        return pffft.get(key.key) > 0;
    }

    public static Vector2D getInputVector(){
        float x = 0;
        float y = 0;

        if(Input.isDown(Input.InputKey.RIGHT)) x++;
        if(Input.isDown(Input.InputKey.LEFT)) x--;
        if(Input.isDown(Input.InputKey.DOWN)) y--;
        if(Input.isDown(Input.InputKey.UP)) y++;

        //Vector fix
        if(x != 0 && y != 0){
            x *= 0.7071f;
            y *= 0.7071f;
        }

        return new Vector2D(x, y);
    }


    //just pretend this is set up correctly :(
    public Input(){

        for(InputKey a : InputKey.values()){
            pffft.put(a.key, 0);
        }

        Renderer.instance.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                pffft.put(e.getKeyCode(), 0);
//                super.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                pffft.compute(e.getKeyCode(), (_, v) -> v + 1);
//                System.out.println(e.getKeyCode());
//                super.keyPressed(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
//                super.keyTyped(e);
            }
        });

    }

}
