package engine.example;

import engine.render.SpriteFont;

public class DefaultFont extends SpriteFont {
    public DefaultFont(String text, float x, float y) {
        super(text, x, y);
    }

    @Override
    public char[] getCharacters() {
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789?!.:;,()[]\'\"-/ÄÖÜäöüëïß%@<>".toCharArray();
    }

    @Override
    public String getAsset() {
        return "example/highContrastFont.png";
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
        return false;
    }
}