package engine.render;

import engine.math.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class SpriteFont extends DisplayObject {

    private static final HashMap<Class<? extends SpriteFont>, Map<Character, Integer>> classToCharacterMap = new HashMap<>();

    private final ArrayList<SpriteSheet> sprites = new ArrayList<>();
    private String text;

    private SpriteFont(String text){
        initialiseFont(this);
        setText(text);
        this.registerRender();
    }

    public SpriteFont(String text, Vector2D position){
        this(text);
        setPosition(position);
    }

    public SpriteFont(String text, float x, float y){
        this(text);
        setPosition(x, y);
    }

    public void setText(String text){
        this.text = forceUpperCase()? text.toUpperCase() : text;
        updateText();
    }

    //TODO: clean up
    public void updateText() {
        Map<Character, Integer> characterToFrame = getCharacterMap();

        sprites.forEach(e -> e.setVisible(false));

        width = 0;
        height = 0;

        int x = 0;
        int y = 0;
        int i = 0;
        for(char character : text.toCharArray()){
            if(character == '\n'){
                x = 0;
                y += (int) (getFrameHeight() * scale);

                if(y > height) height = y;

                continue;
            }

            if(characterToFrame.containsKey(character)){
                if(sprites.size() <= i){
                    sprites.add(createNewSprite());
                }

                SpriteSheet sprite = sprites.get(i);

                sprite.setFrame(characterToFrame.get(character));
                sprite.setVisible(true);
                sprite.setScale(scale);
                sprite.setPosition(x, y);

                i++;
            }

            x += (int) (getFrameWidth() * scale);

            if(x > width){
                width = x;
            }
        }

        height += (int) (getFrameHeight() * scale);

        translateSprites(position);
    }

    public SpriteSheet createNewSprite(){
        SpriteSheet sprite = new SpriteSheet(getAsset(), getFrameWidth(), getFrameHeight()){
            @Override public void registerRender() {;}
        };

        sprite.setOriginX(0);
        sprite.setOriginY(0);

        return sprite;
    }

    // TODO: reduce this redundancy
    @Override
    public void setPosition(Vector2D to){
        Vector2D change = position.copy();

        super.setPosition(to);

        change.subtract(position);
        change.scale(-1);

        translateSprites(change);
    }

    @Override
    public void setPosition(float x, float y){
        Vector2D change = position.copy();

        super.setPosition(x, y);

        change.subtract(position);
        change.scale(-1);
        translateSprites(change);
    }

    public void draw(Graphics2D g){
        sprites.forEach(e -> e.draw(g));
    }

    private void translateSprites(Vector2D vector){
        sprites.forEach(e -> e.move(vector));
    }

    @Override
    public float getWidth(){
        return width;
    }

    @Override
    public float getHeight(){
        return height;
    }

    @Override
    public void setScale(float scale){
        super.setScale(scale);
        updateText();
    }

    /**
     * @return an array of characters in order that they appear
     * in the sprite sheet
     */
    public abstract char[] getCharacters();
    /**
     * @return the filepath to the sprite sheet
     */
    public abstract String getAsset();
    /**
     * @return the width in pixels of each character
     */
    public abstract int getFrameWidth();
    /**
     * @return the height in pixels of each character
     */
    public abstract int getFrameHeight();
    /**
     * @return if all input characters should be treated
     * as upper-case
     */
    public abstract boolean forceUpperCase();

    public final Map<Character, Integer> getCharacterMap(){
        return classToCharacterMap.get(getClass());
    }

    public static void initialiseFont(SpriteFont spriteFont){
        if(classToCharacterMap.containsKey(spriteFont.getClass())) return;

        HashMap<Character, Integer> characterMap = new HashMap<>();

        char[] chars = spriteFont.getCharacters();
        for(int i = 0; i < chars.length; i++){
            characterMap.put(chars[i], i);
        }

        classToCharacterMap.put(spriteFont.getClass(), characterMap);
    }

}
