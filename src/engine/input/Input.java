package engine.input;

import engine.Game;
import engine.math.Vector2D;
import engine.render.display.Display;
import engine.render.display.Screen;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;

public class Input{

    public enum InputKey {
        UP(87),
        LEFT(65),
        DOWN(83),
        RIGHT(68),
        SPACE(32);

        public final int key;

        InputKey(int key){
            this.key = key;
        }
    }

    private final HashMap<Integer, Boolean> keyDownMap = new HashMap<>();
    private final HashMap<Integer, Integer> keyDurationMap = new HashMap<>();

    private final Vector2D lastMousePosition = new Vector2D();

    public Vector2D getMousePositionGameRelative(){
        Vector2D position = lastMousePosition.copy();
        Display display = Game.getInstance().getDisplay();

        position.add(display.getDisplayOriginX(), display.getDisplayOriginY());
        return position;
    }

    public Vector2D getMousePositionScreenRelative(){
        return lastMousePosition.copy();
    }

    /**
     * Returns true while a key is being pressed.
     */
    public boolean isDown(InputKey key){
        boolean down = keyDownMap.get(key.key);

        if(down){
            keyDurationMap.compute(key.key, (_, v) -> v + 1);
        }

        return down;
    }

    /**
     * Returns true once only for the duration of the key press.
     */
    public boolean isPressed(InputKey key){
        isDown(key);
        return keyDurationMap.get(key.key) == 1;
    }

    public Vector2D getInputVector(){
        float x = 0;
        float y = 0;

        if(isDown(Input.InputKey.RIGHT)) x++;
        if(isDown(Input.InputKey.LEFT)) x--;
        if(isDown(Input.InputKey.DOWN)) y++;
        if(isDown(Input.InputKey.UP)) y--;

        //Vector fix
        if(x != 0 && y != 0){
            x *= 0.7071f;
            y *= 0.7071f;
        }

        return new Vector2D(x, y);
    }

    public Input(){
        for(InputKey input : InputKey.values()){
            keyDownMap.put(input.key, false);
            keyDurationMap.put(input.key, 0);
        }

        Display display = Game.getInstance().getDisplay();
        Screen screen = display.getScreen();

        display.addKeyListener(new KeyHandler());
        display.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                lastMousePosition.setTo(screen.getLocalPositionFromGlobal(e.getX(), e.getY()));
            }
        });
    }

    private class KeyHandler extends KeyAdapter{
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
