package engine.render;

import engine.math.Vector2D;

import java.awt.*;

public class SpriteSheet extends Sprite{

    private final int frameWidth, frameHeight;
    private final int framesPerRow, framesPerColumn;
    private final int totalFrames;

    private int cropX1, cropY1, cropX2, cropY2;

    private int frame;

    protected SpriteSheet(String asset, int frameWidth, int frameHeight) {
        super(asset);

        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        framesPerRow = width / frameWidth;
        framesPerColumn = height / frameHeight;

        totalFrames = framesPerRow * framesPerColumn;

        setFrame(0);
    }

    public SpriteSheet(String asset, int frameWidth, int frameHeight, float x, float y){
        this(asset, frameWidth, frameHeight);
        setPosition(x, y);
    }

    public SpriteSheet(String asset, int frameWidth, int frameHeight, Vector2D position){
        this(asset, frameWidth, frameHeight);
        setPosition(position);
    }

    @Override
    public float getWidth(){
        return frameWidth * scale;
    }

    @Override
    public float getHeight(){
        return frameHeight * scale;
    }

    /**
     * @return the current frame of the sprite
     */
    public int getFrame(){
        return frame;
    }

    /**
     * Sets the frame of the sprite. Frames are ordered
     * in the sprite sheet starting at 0. Frame 0 is located
     * at the top left hand corner. Subsequent frame indexes
     * move to the right until the end of a row, where the numbering
     * continues on the next line down.
     */
    public void setFrame(int frameIndex){
        frame = frameIndex % totalFrames;

        int column = frame % framesPerRow;
        int row = frame / framesPerRow;

        cropX1 = column * frameWidth;
        cropY1 = row * frameHeight;

        cropX2 = (column + 1) * frameWidth;
        cropY2 = (row + 1) * frameHeight;
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h) {
        g.drawImage(image, x, y,x + w,y + h, cropX1, cropY1, cropX2, cropY2, null);
    }

}
