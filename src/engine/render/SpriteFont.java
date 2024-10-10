package engine.render;

import engine.math.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class SpriteFont extends DisplayObject {

    private final Map<Character, Integer> characterToFrame;

    private final ArrayList<SpriteSheet> sprites;
    private String text;

    private boolean requiresUpdate = true;

    private SpriteFont(String text){
        sprites = new ArrayList<>();
        characterToFrame = new HashMap<>();

        char[] chars = getCharacters();
        for(int i = 0; i < chars.length; i++){
            characterToFrame.put(chars[i], i);
        }

        setOrigin(0, 0);

        setText(text);
    }

    /**
     * Creates a new spritefont containing param text as text
     * at position param position
     */
    public SpriteFont(String text, Vector2D position){
        this(text);
        setPosition(position);
    }

    /**
     * Creates a new spritefont containt param text as text
     * at position params x and y
     */
    public SpriteFont(String text, float x, float y){
        this(text);
        setPosition(x, y);
    }

    /**
     * Sets the text of the spritefont
     */
    public void setText(String text){
        this.text = forceUpperCase()? text.toUpperCase() : text;
        requiresUpdate = true;
    }

    //TODO: clean up
    private void updateText() {
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

        Vector2D topLeftPosition = position.copy();
        topLeftPosition.add(-width * originX, -height * originY);

        moveSprites(topLeftPosition);
    }

    private SpriteSheet createNewSprite(){
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

        moveSprites(change);
    }

    @Override
    public void setPosition(float x, float y){
        Vector2D change = position.copy();

        super.setPosition(x, y);

        change.subtract(position);
        change.scale(-1);

        moveSprites(change);
    }

    @Override
    public void draw(Graphics2D g){
        if(requiresUpdate){
            updateText();
            requiresUpdate = false;
        }
        sprites.forEach(e -> e.draw(g));
    }

    // This is a consequence of setting up the way that I did :/
    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h){
        ;
    }

    /**
     * translates the positions of all letter sprites by param delta
     */
    private void moveSprites(Vector2D delta){
        sprites.forEach(e -> e.move(delta));
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
        requiresUpdate = true;
    }

    @Override
    public void setOriginX(float originX){
        super.setOriginX(originX);
        requiresUpdate = true;
    }

    @Override
    public void setOriginY(float originY){
        super.setOriginY(originY);
        requiresUpdate = true;
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

}