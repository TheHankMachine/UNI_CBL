package engine.math;

import java.util.EnumMap;
import java.util.function.BiFunction;

import static engine.math.Axis2D.*;

//Works Cited: Björn Baumeier
public class Vector2D extends EnumMap<Axis2D, Float> {

    /**
     * initialises a zero vector
     */
    public Vector2D(){
        this(0, 0);
    }

    /**
     * initialises a vector of x and y
     */
    public Vector2D(float x, float y){
        super(Axis2D.class);

        put(X, x);
        put(Y, y);
    }

    /**
     * initialises a copy of the param from
     */
    public Vector2D(Vector2D from){
        this(from.get(X), from.get(Y));
    }

    /**
     * sets the vector to params x and y
     */
    public void setTo(float x, float y){
        put(X, x);
        put(Y, y);
    }

    /**
     * sets the vector to be the same as param copy
     */
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
     * @return the angle of the vector about the
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

    /**
     * Adds param vector to this vector
     */
    public void add(Vector2D vector){
        computeForEach((k, v) -> v + vector.get(k));
    }

    /**
     * Adds params x and y to their respective axis
     */
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
     * Interpolates this vector to param to at a fixed rate
     * (param rate)
     */
    public void interpolate(Vector2D to, float rate){
        Vector2D delta = to.copy();
        delta.subtract(this);

        if(delta.getLength() <= rate){
            setTo(to);
        }else{
            delta.normalise(rate);
            add(delta);
        }
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

    private void computeForEach(BiFunction<Axis2D, Float, Float> operator){
        forEach((k, _) -> compute(k, operator));
    }

}