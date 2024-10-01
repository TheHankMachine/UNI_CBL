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

    public float getLength(){
        return (float) Math.hypot(get(X), get(Y));
    }

    public void add(Axis2D axis, float delta){
        compute(axis, (k, v) -> v + delta);
    }

    public void scale(float scalar){
        computeForEach((k, v) -> v * scalar);
    }

    public void add(Vector2D vector){
        computeForEach((k, v) -> v + vector.get(k));
    }

    public void normalise(){
        normalise(1);
    }

    public void normalise(float to){
        scale(to / getLength());
    }

    private void computeForEach(BiFunction<Axis2D, Float, Float> operator){
        forEach((k, _) -> compute(k, operator));
    }

}
