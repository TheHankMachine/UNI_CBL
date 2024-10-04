package engine.render;

import engine.math.Axis2D;
import engine.math.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class TextSprite implements Renderable {

    //TODO: kill it. burn it. do what ever the fuck you need to get rid of this shit.
    private static final HashMap<Class<? extends TextSprite>, HashMap<Character, Integer>> classToCharacterMap = new HashMap<>();

    private final ArrayList<SpriteSheet> sprites = new ArrayList<>();
    private Vector2D position = new Vector2D();
    private String text;

    private float scale = 3;

    private int width;
    private int height;

    private TextSprite(String text){
        initialiseFont(this);
        setText(text);
        this.registerRender();
    }

    public TextSprite(String text, Vector2D position){
        this(text);
        setPosition(position);
    }

    public TextSprite(String text, float x, float y){
        this(text);
        setPosition(x, y);
    }

    public void setText(String text){
        this.text = text.toUpperCase();
        updateText();
    }

    //TODO: clean up
    public void updateText() {
        Map<Character, Integer> charMap = getCharacterMap();

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

                if(y > height){
                    height = y;
                }

                continue;
            }

            if(charMap.containsKey(character)){
                if(sprites.size() <= i){
                    sprites.add(addSprite());
                }

                SpriteSheet sprite = sprites.get(i);

                sprite.setFrame(charMap.get(character));
                sprite.setPosition(x, y);
                sprite.setScale(scale);
                sprite.setVisible(true);

                i++;
            }

            x += (int) (getFrameWidth() * scale);

            if(x > width){
                width = x;
            }
        }

        height += (int) (getFrameHeight() * scale);

        translate(position);

    }

    public SpriteSheet addSprite(){
        SpriteSheet sprite = new SpriteSheet(
            getAsset(), getFrameWidth(), getFrameHeight()
        ){
            @Override public void registerRender() {;}
        };

        sprite.setOrigin(0, 0);

        return sprite;
    }

    public void setPosition(Vector2D to){
        Vector2D change = position.copy();

        position.setTo(to);

        change.subtract(position);
        change.scale(-1);
        translate(change);
    }

    public void setPosition(float x, float y){
        Vector2D change = position.copy();

        position.setTo(x, y);

        change.subtract(position);
        change.scale(-1);
        translate(change);
    }

    public void translate(Vector2D vector){
        sprites.forEach(e -> e.move(vector));
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public void draw(Graphics2D g){
        sprites.forEach(e -> e.draw(g));
    }

    public abstract char[] getCharacters();
    public abstract String getAsset();
    public abstract int getFrameWidth();
    public abstract int getFrameHeight();

    public final Map<Character, Integer> getCharacterMap(){
        return classToCharacterMap.get(getClass());
    }

    public static void initialiseFont(TextSprite textSprite){
        if(classToCharacterMap.containsKey(textSprite.getClass())) return;

        HashMap<Character, Integer> characterMap = new HashMap<>();

        char[] chars = textSprite.getCharacters();
        for(int i = 0; i < chars.length; i++){
            characterMap.put(chars[i], i);
        }

        classToCharacterMap.put(textSprite.getClass(), characterMap);
    }

}
