import render.Renderable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import math.Vector2D;


//TODO: make me abstract
public class Sprite implements Renderable {

    protected final BufferedImage image;

    protected int scale = 10;

    protected Vector2D position = new Vector2D(100, 100);

//    protected float x = (float) (Math.random() * (Renderer.width() - scale * 15));
//    protected float y = (float) (Math.random() * (Renderer.height() - scale * 15));


    private final int frameWidth = 15;
    private final int frameHeight = 12;

    private final int framesPerRow, framesPerColumn;
    private final int totalFrames;

    private int frame = 0;

    private int cropX1, cropY1, cropX2, cropY2;

    public Sprite(){
        image = loadImage("dvd.png");

        framesPerRow = image.getWidth() / frameWidth;
        framesPerColumn = image.getHeight() / frameHeight;

        totalFrames = framesPerRow * framesPerColumn;

        setFrame(0);

        registerRender();
    }

    public void setFrame(int frameIndex){
        //swagalicious
        frame = frameIndex % totalFrames;

        int row = frame % framesPerRow;
        int column = frame / framesPerColumn;

        cropX1 = row * frameWidth;
        cropY1 = column * frameHeight;

        cropX2 = (row + 1) * frameWidth;
        cropY2 = (column + 1) * frameHeight;
    }

    @Override
    public void draw(Graphics g) {


//        g.drawImage(image,
//            (int) x, (int) y,
//            (int) (x - frameWidth * scale),
//            (int) (y + frameHeight * scale),
//            cropX1, cropY1, cropX2, cropY2,
//            null
//        );



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
