package engine.math;

public interface Collideable {

    BoundingBox getBoundingBox();

    static boolean collides(Vector2D point, Collideable collideable){
        return collideable.getBoundingBox().contains(point);
    }
}
