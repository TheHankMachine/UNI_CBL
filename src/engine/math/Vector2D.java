package engine.math;

import java.util.EnumMap;
import java.util.function.BiFunction;

import static engine.math.Axis2D.*;

//Works Cited: Björn Baumeier
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
     * @returns the angle of the vector about the
     * horizontal. Counterclockwise relative to
     * cartesian coordinates, clockwise relative
     * to the screen.
     */
    public float getAngle(){
        return (float) Math.atan2(get(Y), get(X));
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

    public void add(float x, float y){
        compute(X, (_, v) -> v + x);
        compute(Y, (_, v) -> v + y);
    }

    /**
     * Does the exact same thing as add, but the other way.
     * Crazy right?
     */
    public void subtract(Vector2D vector){
        computeForEach((k, v) -> v - vector.get(k));
    }

    /**
     * Reflects the vector across all axes
     */
    public void flip(){
        computeForEach((_, v) -> -v);
    }

    /**
     * Reflects the vector on the respective axis
     */
    public void flip(Axis2D axis){
        compute(axis, (_, v) -> -v);
    }

    /**
     * Reflects the vector across another vector
     */
    public void flip(Vector2D vector){
        Vector2D n = vector.copy();
        n.normalise();

        var scale = 2 * dot(n);
        n.scale(scale);

        flip();
        add(n);
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


    /**
     * Rotates the vector around angle. Rotation is counterclockwise
     * relative to cartesian coordinates, but clockwise relative
     * to the screen display.
     *
     * @param angle radian amount to rotate the vector
     */
    public void rotate(float angle){
        float x = get(X);
        float y = get(Y);

        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        put(X, x * cos - y * sin);
        put(Y, x * sin + y * cos);
    }

    /**
     * @return a new vector with same values as this
     */
    public Vector2D copy(){
        return new Vector2D(this);
    }

    private void computeForEach(BiFunction<Axis2D, Float, Float> operator){
        forEach((k, _) -> compute(k, operator));
    }

    /**
     * @return the dot product between this vector and param b
     */
    public float dot(Vector2D b){
        float sum = 0;
        for(Axis2D axis : Axis2D.values()){
            sum += get(axis) * b.get(axis);
        }
        return sum;
    }

}
