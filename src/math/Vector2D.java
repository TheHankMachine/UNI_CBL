package math;

import java.util.EnumMap;
import java.util.function.BiFunction;

import static math.Axis2D.*;

public class Vector2D extends EnumMap<Axis2D, Float> {

    public Vector2D(){
        this(0, 0);
    }

    public Vector2D(float x, float y){
        super(Axis2D.class);

        put(X, x);
        put(Y, y);
    }

    public Vector2D(Vector2D from){
        this(from.get(X), from.get(Y));
    }

    public void setTo(float x, float y){
        put(X, x);
        put(Y, y);
    }

    public void setTo(Vector2D copy){
        computeForEach((k, _) -> copy.get(k));
    }

    /**
     * @return the length of the vector
     */
    public float getLength(){
        return (float) Math.hypot(get(X), get(Y));
    }

    /**
     * Adds to a specified axis of the vector.
     *
     * @param axis to add to
     * @param delta the amount to add
     */
    public void add(Axis2D axis, float delta){
        compute(axis, (_, v) -> v + delta);
    }

    public void add(Vector2D vector){
        computeForEach((k, v) -> v + vector.get(k));
    }

    /**
     * Scales the vector.
     *
     * @param scalar the amount to multiply the vector by
     */
    public void scale(float scalar){
        computeForEach((_, v) -> v * scalar);
    }

    /**
     * Normalises the vector to length 1
     */
    public void normalise(){
        normalise(1);
    }

    /**
     * Normalises the vector to specified length
     *
     * @param to the length to set the vector to
     */
    public void normalise(float to){
        scale(to / getLength());
    }

    public Vector2D copy(){
        return new Vector2D(this);
    }

    private void computeForEach(BiFunction<Axis2D, Float, Float> operator){
        forEach((k, _) -> compute(k, operator));
    }

    /**
     * I doubt this will ever get used. Nevertheless, I put it
     * here anyway
     *
     * @param a vector a
     * @param b vector b
     * @return dot product between a and b
     */
    public static float dot(Vector2D a, Vector2D b){
        float sum = 0;
        for(Axis2D axis : Axis2D.values()){
            sum += a.get(axis) * b.get(axis);
        }
        return sum;
    }

}
