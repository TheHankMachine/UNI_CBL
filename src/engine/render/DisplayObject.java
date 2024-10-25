package engine.render;

import engine.Game;
import engine.math.BoundingBox;
import engine.math.Collideable;
import engine.math.Vector2D;
import engine.math.Axis2D;

import java.awt.*;

public abstract class DisplayObject implements Renderable, Collideable {

    protected Vector2D position = new Vector2D();

    protected float scale = Game.getInstance().getDefaultScale();
    protected float originY = 0.5f;
    protected float originX = 0.5f;

    protected boolean visible = true;
    protected boolean flipX, flipY;

    protected int width;
    protected int height;

    protected DisplayObject(){
        registerRender();
    }

    /**
     * Sets the visibility of the object
     */
    public void setVisible(boolean visible){
        this.visible = visible;
    }

    /**
     * @return the object's visibility state
     */
    public boolean getVisible(){
        return visible;
    }

    /**
     * Sets the scale of the object
     */
    public void setScale(float scale){
        this.scale = scale;
    }

    /**
     * @return the scale of the object
     */
    public float getScale(){
        return scale;
    }

    /**
     * returns the width of the object after scaling
     */
    public float getWidth(){
        return width * scale;
    }

    /**
     * returns the height of the object after scaling
     */
    public float getHeight(){
        return height * scale;
    }

    /**
     * moves the position of the object by param delta
     */
    public void move(Vector2D delta){
        position.add(delta);
    }

    /**
     * moves the position of the object by params x and y
     */
    public void move(float x, float y){
        position.add(x, y);
    }

    /**
     * moves the object's position on a specified axis
     */
    public void move(Axis2D axis, float delta){
        position.add(axis, delta);
    }

    /**
     * Sets the position of the object
     */
    public void setPosition(Vector2D to){
        position.setTo(to);
    }

    /**
     * Sets the position of the object
     */
    public void setPosition(float x, float y){
        position.setTo(x, y);
    }

    /**
     * Sets the position vector to the reference of
     * another vector
     */
    public void setPositionReference(Vector2D ref){
        position = ref;
    }

    /**
     * Returns the position vector of the object
     */
    public Vector2D getPosition(){
        return position;
    }

    /**
     * @return the x value of the object's position
     */
    public float getX(){
        return position.get(Axis2D.X);
    }

    /**
     * @return the y value of the object's position
     */
    public float getY(){
        return position.get(Axis2D.Y);
    }

    /**
     * @return whether the object is reflected vertically
     */
    public boolean getFlipX(){
        return flipX;
    }

    /**
     * @return whether the object is reflected horizontally
     */
    public boolean getFlipY(){
        return flipY;
    }

    /**
     * Sets the object to be mirrored on a vertical plane
     */
    public void setFlipX(boolean flipX){
        this.flipX = flipX;
    }

    /**
     * Sets the object to be mirrored on a horizontal plane
     */
    public void setFlipY(boolean flipY){
        this.flipY = flipY;
    }

    /**
     * Sets the x display origin of the object
     * @param originX value between 0 and 1 inclusive
     */
    public void setOriginX(float originX){
        this.originX = originX;
    }

    /**
     * Sets the y display origin of the object
     * @param originY value between 0 and 1 inclusive
     */
    public void setOriginY(float originY){
        this.originY = originY;
    }

    /**
     * Sets the dispaly origin of the object
     * @param originX value between 0 and 1 inclusive
     * @param originY value between 0 and 1 inclusive
     */
    public void setOrigin(float originX, float originY){
        this.originX = originX;
        this.originY = originY;
    }

    // Congrats, you have scrolled to the
    // important part of the code

    /***
     * @return the bounding box taken up by the sprite
     */
    @Override
    public BoundingBox getBoundingBox() {
        float x = position.get(Axis2D.X);
        float y = position.get(Axis2D.Y);

        float ox = originX;
        float oy = originY;

        float w = getWidth();
        float h = getHeight();

        if(flipX) {
            w = -w;
            ox = 1 - ox;
        }
        if(flipY) {
            h = -h;
            oy = 1 - oy;
        }

        x -= w * ox;
        y -= h * oy;

        return new BoundingBox(x, y, x + w, y + h);
    }


    /**
     * Calls the abstract draw providing x and y
     * (after offsetting for the origin)
     * as well as width and height (after accounting
     * for scaling and mirroring)
     */
    @Override
    public void draw(Graphics2D g) {
        if(!visible) return;

        // bounding box is not called here for it being too
        // slow to create new objects

        int x = position.get(Axis2D.X).intValue();
        int y = position.get(Axis2D.Y).intValue();

        float ox = originX;
        float oy = originY;

        int w = (int) getWidth();
        int h = (int) getHeight();

        if(flipX) {
            w = -w;
            ox = 1 - ox;
        }
        if(flipY) {
            h = -h;
            oy = 1 - oy;
        }

        x -= (int) (w * ox);
        y -= (int) (h * oy);

        draw(g, x, y, w, h);
    }

    /**
     * Override to draws the object to the graphics2D
     * object g. Unless overridden, draw(g) will pass
     * in the parameters as specified
     * @param x the x of the top left corner where
     *          the object should be drawn (origin
     *          and flip accounted for)
     * @param y the y of the top left corner where
     *          the object should be drawn (origin
     *          and flip accounted for)
     * @param w the width that the object should be
     *          drawn to (scaling accounted for)
     * @param h the height that the object should be
     *          drawn to (scaling account for)
     *
     * NOTE: w and h can be negative due to flipping
     */
    protected abstract void draw(Graphics2D g, int x, int y, int w, int h);

}
