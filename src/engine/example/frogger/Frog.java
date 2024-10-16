package engine.example.frogger;

import engine.Game;
import engine.input.Input;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;

public class Frog extends SpriteSheet implements Updateable {

    private enum Direction{
        UP(0, false, false),
        DOWN(0, false, true),
        LEFT(3, true, false),
        RIGHT(3, false, false);

        public final int frameOffset;
        public final boolean flipX;
        public final boolean flipY;
        Direction(int frameOffset, boolean flipX, boolean flipY){
            this.frameOffset = frameOffset;
            this.flipX = flipX;
            this.flipY = flipY;
        }

    }

    private final Vector2D realPosition = new Vector2D();
    private final Map map;
    private Direction direction = Direction.UP;


    private int animationFrame = 0;
    private int inputCooldown = 0;

    public Frog(Map map) {
        super("/example/frogger/frog.png", 16, 13,
            (Frogger.GRID_WIDTH * 0.5f + 0.5f) * Frogger.GRID_SIZE,
            (Frogger.GRID_HEIGHT - 0.5f) * Frogger.GRID_SIZE
        );

        this.map = map;

        realPosition.setTo(getPosition());

        registerUpdate();
    }

    @Override
    public void update() {
        doInput();
        playAnimation();
        checkCollide();

        Game.getInstance().getDisplay().setDisplayOrigin(
            0,
            getY() - (float) Frogger.GRID_HEIGHT * Frogger.GRID_SIZE / 2
        );
    }

    public void checkCollide(){
        Layer layer = map.getLayer(getY());
        if(layer.checkCollide(getPosition())){
            die();
        }
    }

    public void die(){
        throw new RuntimeException("skill issue");
//        System.out.println("L");
    }

    public void doInput(){
        Input input = Game.getInstance().getInput();

        if(inputCooldown > 0){
            inputCooldown--;
            return;
        }

        if(input.isPressed(Input.InputKey.UP)){
            realPosition.add(0, -Frogger.GRID_SIZE);
            direction = Direction.UP;
        }else if(input.isPressed(Input.InputKey.DOWN)){
            realPosition.add(0, Frogger.GRID_SIZE);
            direction = Direction.DOWN;
        }else if(input.isPressed(Input.InputKey.RIGHT)){
            realPosition.add(Frogger.GRID_SIZE, 0);
            direction = Direction.RIGHT;
        }else if(input.isPressed(Input.InputKey.LEFT)){
            realPosition.add(-Frogger.GRID_SIZE, 0);
            direction = Direction.LEFT;
        }else{
            return;
        }

        animationFrame = 1;
        inputCooldown = (int) (Frogger.GRID_SIZE / Frogger.MOVE_SPEED + 0.5);
    }

    public void playAnimation(){
        getPosition().interpolate(realPosition, Frogger.MOVE_SPEED);

        setFrame(direction.frameOffset + animationFrame / 2);
        setFlipX(direction.flipX);
        setFlipY(direction.flipY);

        if(animationFrame == 0) return;

        if(animationFrame > 4){
            animationFrame = 0;
        }else {
            animationFrame++;
        }
    }
}
