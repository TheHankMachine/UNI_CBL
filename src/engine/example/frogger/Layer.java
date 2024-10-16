package engine.example.frogger;

import engine.math.Collideable;
import engine.math.Vector2D;
import engine.render.DisplayObject;
import engine.render.Renderable;
import engine.render.SpriteSheet;
import engine.update.Updateable;

import java.awt.*;
import java.util.ArrayList;

public class Layer implements Updateable, Renderable {

    private static final int width = 12;

    private ArrayList<Car> cars = new ArrayList<>();

    private int offset = 0;
    private int y;

    private float speed;

    public Layer(int index){
        y = index * Frogger.GRID_SIZE;

        if(index == Frogger.GRID_HEIGHT - 1){
            return;
        }

        speed = (int) Math.signum(Math.random() - 0.5) * (int) (Math.random() * 2 + 1);

        for(
            int x = (int) (Math.random() * Frogger.GRID_WIDTH * Frogger.GRID_SIZE);
            x < Frogger.GRID_WIDTH * Frogger.GRID_SIZE;
            x += (int) Math.max(17, Math.random() * Frogger.GRID_WIDTH * Frogger.GRID_SIZE)
        ) {
            cars.add(new Car(x, y + 2, speed));
        }

        registerRender();
        registerUpdate();
    }

    @Override
    public void draw(Graphics2D g) {
//        g.setColor(new Color(0xc3c3c6));
//
//        g.fillRect(
//            0,
//            y,
//            Frogger.GRID_WIDTH * Frogger.GRID_SIZE,
//            Frogger.GRID_SIZE
//        );

//        g.setColor(Color.getHSBColor((float) Math.random(), 0.5f, 0.5f));
//        for(int i = 0; i < width; i++) {
//            g.drawRect(
//                offset + i * Frogger.GRID_SIZE,
//                y,
//                Frogger.GRID_SIZE,
//                Frogger.GRID_SIZE
//            );
//        }
    }

    @Override
    public DepthLayer getDepth() {
        return DepthLayer.BACKGROUND;
    }

    @Override
    public void update() {

    }

    public boolean checkCollide(Vector2D pos){
        return cars.stream().anyMatch(e -> Collideable.collides(pos, e));
    }

    public class Car extends SpriteSheet implements Updateable{

        private float speed;

        public Car(float x, float y, float speed) {
            super("/example/frogger/cars.png", 16, 7, x, y);

            this.speed = speed;
            setOrigin(0, 0);
            setFrame((int) (Math.random() * 4));
            setFlipX((getFrame() % 2 == 0) == speed < 0);

            registerUpdate();
        }

        @Override
        public void update() {
            move(speed, 0);

            float l = -getWidth();
            float r = Frogger.GRID_WIDTH* Frogger.GRID_SIZE;

            if(getX() > r){
                setPosition(l, getY());
            }
            if(getX() < l){
                setPosition(r, getY());
            }
        }
    }
}
