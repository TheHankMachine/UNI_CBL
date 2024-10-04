package game;

import engine.Game;
import engine.GameConfig;
import engine.math.Vector2D;
import engine.render.Sprite;
import engine.render.SpriteSheet;
import engine.render.TextSprite;

import java.awt.*;

public class PlaneArcade extends Game {

    public TextSprite f;

    public PlaneArcade(){
        super(new GameConfig(320, 240, Color.ORANGE));

        f = new Font1("go fuck\nyourself", 30, 30);
//        f = new SpriteSheet("dvd.png", 15, 12, 50, 50);


        register();
    }

    static class Font1 extends TextSprite {
        public Font1(String text, float x, float y) {
            super(text, x, y);
        }

        @Override
        public char[] getCharacters() {
            return "-ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:".toCharArray();
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
    }

    int x = 30;
    int y = 50;

    int vx = 1;
    int vy = 1;

    @Override
    public void update() {

        x += vx;
        y += vy;

        if(x < 0 || x + f.getWidth() > config.width){
            vx = -vx;
        }

        if(y < 0 || y + f.getHeight() > config.height){
            vy = -vy;
        }

//        f.setFlip(
//            vx < 0,
//            vy < 0
//        );

        f.setPosition(x, y);

//        f.setText(Integer.toString((int) (Math.random() * (2 << 16))));
    }
}
