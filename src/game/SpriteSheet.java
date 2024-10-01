package game;

import math.Axis2D;

import java.awt.*;

public abstract class SpriteSheet extends Sprite{

    private final int frameWidth, frameHeight;
    private final int framesPerRow, framesPerColumn;
    private final int totalFrames;

    private int cropX1, cropY1, cropX2, cropY2;
    private int frame;

    public SpriteSheet(String asset, int frameWidth, int frameHeight) {
        super(asset);

        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        framesPerRow = width / frameWidth;
        framesPerColumn = height / frameHeight;

        totalFrames = framesPerRow * framesPerColumn;

        setFrame(0);
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
        int x = position.get(Axis2D.X).intValue();
        int y = position.get(Axis2D.Y).intValue();

        int width = (int) (frameWidth * scale);
        if(flipX){
            width = -width;
        }
        x -= width / 2;

        int height = (int) (frameHeight * scale);
        if(flipY){
            height = -height;
        }
        y -= height;

        g.drawImage(image, x, y,x + width,y + height, cropX1, cropY1, cropX2, cropY2, null);
    }

}
