package game;

import engine.Game;
import engine.GameConfig;
import engine.Util.DefaultFont;
import engine.render.SpriteFont;

import java.awt.*;

public class PlaneArcade extends Game {

    public SpriteFont f;

    public PlaneArcade(){
        super(new GameConfig(320, 240, Color.ORANGE));

        f = new DefaultFont("Testing new font set.\nIt\'s taken from pokemon\nand it has a ton of punctation.\n.,:;?![]()", 30, 30);

        register();
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

        f.setPosition(x, y);
    }
}
