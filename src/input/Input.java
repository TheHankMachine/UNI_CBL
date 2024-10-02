package input;

import math.Vector2D;
import render.Renderer;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;

public class Input {

    public enum InputKey {
        SPACE(32),
        UP(87),
        LEFT(65),
        DOWN(83),
        RIGHT(68);

        public final int key;

        InputKey(int key){
            this.key = key;
        }
    }

    private static HashMap<Integer, Boolean> keyDownMap = new HashMap<>();
    private static HashMap<Integer, Integer> keyDurationMap = new HashMap<>();

    private static Point lastMouseLocation;

    /**
     * Returns true while a key is being pressed.
     */
    public static boolean isDown(InputKey key){
        boolean down = keyDownMap.get(key.key);

        if(down){
            keyDurationMap.compute(key.key, (_, v) -> v + 1);
        }

        return down;
    }

    /**
     * Returns true once only for the duration of the key press.
     */
    public static boolean isPressed(InputKey key){
        isDown(key);
        return keyDurationMap.get(key.key) == 1;
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
            keyDownMap.put(a.key, false);
            keyDurationMap.put(a.key, 0);
        }

        Renderer.instance.addKeyListener(new KeyHandler());
        Renderer.instance.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                lastMouseLocation = e.getPoint();
            }
        });
    }

    private static class KeyHandler extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if(!keyDownMap.containsKey(keyCode)) return;

            keyDownMap.put(keyCode, false);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if(!keyDownMap.containsKey(keyCode)) return;

            keyDownMap.put(keyCode, true);
        }
    }

}
