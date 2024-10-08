package engine.math;

public class BoundingBox{
    public final float x1, y1, x2, y2, width, height;

    public BoundingBox(float x1, float y1, float x2, float y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = this.x2 - this.x1;
        this.height = this.y2 - this.y1;
    }

    public float getCenterX(){
        return (x1 + x2) / 2;
    }

    public float getCenterY(){
        return (y1 + y2) / 2;
    }

    public boolean contains(Vector2D point){
        return Math.abs(getCenterX() - point.get(Axis2D.X)) < Math.abs(width) / 2 &&
                Math.abs(getCenterY() - point.get(Axis2D.Y)) < Math.abs(height) / 2;
    }

}