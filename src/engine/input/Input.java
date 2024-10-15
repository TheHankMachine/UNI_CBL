package engine.input;

import engine.Game;
import engine.math.Vector2D;
import engine.render.display.Display;
import engine.render.display.Screen;

import java.awt.event.*;
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
        display.addMouseListener(new ClickListener());
    }

    /**
     * @return the position of the mouse relative to the game.
     * (after offsetting the display origin)
     */
    public Vector2D getMousePositionGameRelative(){
        Vector2D position = lastMousePosition.copy();
        Display display = Game.getInstance().getDisplay();

        position.add(display.getDisplayOriginX(), display.getDisplayOriginY());
        return position;
    }

    /**
     * @return the position of the mouse relative to the screen.
     * (not accounting for display origin offset)
     */
    public Vector2D getMousePositionScreenRelative(){
        return lastMousePosition.copy();
    }

    /**
     * @return if a key is being held down. returns true
     * for the entire duration of a keypress
     */
    public boolean isDown(InputKey key){
        boolean down = keyDownMap.get(key.key);

        if(down){
            keyDurationMap.compute(key.key, (_, v) -> v + 1);
        }

        return down;
    }

    /**
     * @return if a key has been pressed. returns true only once
     * for the duration of the key press.
     */
    public boolean isPressed(InputKey key){
        isDown(key);
        return keyDurationMap.get(key.key) == 1;
    }

    /**
     * @return the input from the WASD keys, normalised
     * to a length of 1;
     */
    public Vector2D getInputVector(){
        float x = 0;
        float y = 0;

        if(isDown(Input.InputKey.RIGHT)) x++;
        if(isDown(Input.InputKey.LEFT)) x--;
        if(isDown(Input.InputKey.DOWN)) y++;
        if(isDown(Input.InputKey.UP)) y--;

        //normalisation:
        if(x != 0 && y != 0){
            x *= 0.7071f;
            y *= 0.7071f;
        }

        return new Vector2D(x, y);
    }

    private class KeyHandler extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if(!keyDownMap.containsKey(keyCode)) return;

            keyDownMap.put(keyCode, false);
            keyDurationMap.put(keyCode, 0);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if(!keyDownMap.containsKey(keyCode)) return;

            keyDownMap.put(keyCode, true);
        }
    }

}
