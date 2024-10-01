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
        if(Input.isDown(Input.InputKey.DOWN)) y++;
        if(Input.isDown(Input.InputKey.UP)) y--;

        //Vector fix
        if(x != 0 && y != 0){
            x *= 0.7071f;
            y *= 0.7071f;
        }

        return new Vector2D(x, y);
    }


    public Input(){

        for(InputKey a : InputKey.values()){
            pffft.put(a.key, 0);
        }

        Renderer.instance.addKeyListener(new KeyHandler());
    }

    private static class KeyHandler extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if(!pffft.containsKey(keyCode)) return;

            pffft.put(keyCode, 0);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if(!pffft.containsKey(keyCode)) return;

            pffft.compute(keyCode, (_, v) -> v + 1);
        }
    }

}
