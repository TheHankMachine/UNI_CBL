package game;

import engine.render.SpriteFont;

public class Text extends SpriteFont {
    
    public Text(String text, float x, float y) {
        super(text, x, y);

        setOrigin(0.5f, 0.5f);
    }

    
    @Override
    public DepthLayer getDepth() {
        return DepthLayer.UI;
    } 

    @Override
    public char[] getCharacters() {
        return " ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:".toCharArray();
    }

    @Override
    public String getAsset() {
        return "font1.png";
    }

    @Override
    public int getFrameWidth() {
        return 8;
    }

    @Override
    public int getFrameHeight() {
        return 8;
    }

    @Override
    public boolean forceUpperCase() {
        return true; 
    }
}
