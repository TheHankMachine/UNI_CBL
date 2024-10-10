package engine.math;

public interface Collideable {

    /**
     * Returns the bounding box of the object
     */
    BoundingBox getBoundingBox();

    /**
     * @return if point is inside the collideable's bouding box
     */
    static boolean collides(Vector2D point, Collideable collideable){
        return collideable.getBoundingBox().contains(point);
    }
}