package game;

import math.Axis2D;
import math.Vector2D;
import render.Renderable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Sprite implements Renderable {

    protected final BufferedImage image;

    protected Vector2D position = new Vector2D();
    protected float scale = 1;
    protected boolean flipX = false;
    protected boolean flipY = false;

    protected int width;
    protected int height;

    public Sprite(String asset){
        image = loadImage(asset);

        width = image.getWidth();
        height = image.getHeight();

        registerRender();
    }

    @Override
    public void draw(Graphics g) {
        int x = position.get(Axis2D.X).intValue();
        int y = position.get(Axis2D.Y).intValue();

        int w = (int) (width * scale);
        if(flipX) w = -w;
        x -= w / 2;

        int h = (int) (height * scale);
        if(flipY) h = -h;
        y -= h / 2;

        g.drawImage(image, x, y,x + width, y + height, null);
    }

    public static BufferedImage loadImage(String assetName){
        File file = new File(Config.ASSET_FILE_PATH + assetName);

        try{
             return ImageIO.read(file);
        }catch(IOException e){
            throw new RuntimeException(String.format("File not found:\n%s", file.getAbsolutePath()));
        }
    }

}
