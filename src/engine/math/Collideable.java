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

    /**
     * @return if a collideable's bounding box contains a points
     */
    static boolean collides(Collideable collideable, Vector2D point){
        return collides(point, collideable);
    }

    /**
     * @return if two collideable's bounding boxes have overlap
     */
    static boolean collides(Collideable obj1, Collideable obj2){
        return obj1.getBoundingBox().intersects(obj2.getBoundingBox());
    }
}