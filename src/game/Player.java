package game;

import engine.Game;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;
import java.util.HashSet;

public class Player extends SpriteSheet implements Updateable {

    // PI
    private final float PI = (float) Math.PI;

    // the angle of player's rotation
    float currentAngle = 0;

    // the speed of player's rotation
    private final float rotationSpeed = 0.34f;

    // the minimum angle of player's rotation
    private final float rotationStep = PI / 8;

    // the speed of player's movement
    private final float speed = 8f;

    // top left corner of the last chunk that the player was in, used to render clouds
    int lastChunkX = 0;
    int lastChunkY = 0;

    // set of clouded chunks
    HashSet<Chunk> cloudedChunks = new HashSet<>();

    // screen dimensions
    int screenWidth = Game.getInstance().getDisplayWidth();
    int screenHeight = Game.getInstance().getDisplayHeight();

    public Player() {
        super("player.png", 16, 16,
                (float) ((Game.getInstance().getDisplayWidth() / 2)),
                (float) ((Game.getInstance().getDisplayHeight() / 2)));

        setScale(4);

        // setting the origin of coordinate system
        setOrigin(0, 0);

        // setting the initial player sprite
        setFrame(0);

        new CloudChunk(0, 320, 0, 240);
        cloudedChunks.add(new Chunk(0, 0));

        registerUpdate();
    }

    private void rotateToCursor() {
        // getting mouse position
        Vector2D cursorPosition = Game.getInstance().getInput().getMousePositionGameRelative();

        // adjusting the cursor position to be relative to the player
        cursorPosition.subtract(position);
        cursorPosition.subtract(new Vector2D(8, 8));

        // calculating the angle between the X-axis and the vector from the center of the screen to the cursor
        float angle = cursorPosition.getAngle() + PI / 2;

        // converting negative angle values to positive ones
        if (angle < 0) {
            angle = 2 * PI + angle;
        }

        // the difference between the current angle and the angle we want to reach (we use it to make a smooth rotation)
        float diff = currentAngle - angle;

        // handling difference values greater than PI
        if (Math.abs(diff) > PI) {
            diff -= 2 * PI * Math.signum(diff);
        }

        // changing the current angle
        currentAngle -= Math.signum(diff) * rotationSpeed;

        // handling negative angle values
        if (currentAngle < 0) {
            currentAngle += 2 * PI;
        }

        // not changing the current angle if it is lesser or equal to the minimal rotation angle
        if (Math.abs(diff) <= rotationStep) {
            currentAngle = angle;
        }

        // calculating the sprite sheet frame
        int sprite_index = (int) (currentAngle / rotationStep);

        // setting the sprite sheet frame to the calculated index
        setFrame(sprite_index);
    }

    private void move() {
        // calculating the vector along which the player will move
        Vector2D directionVector = new Vector2D(
                (float) Math.sin(currentAngle - (currentAngle % rotationStep)),
                (float) -Math.cos(currentAngle - (currentAngle % rotationStep)));

        // scaling the vector to make the player move with the desired speed
        directionVector.scale(speed);

        // moving the player
        move(directionVector);

        // setting the display origin to the current position of the player
        Game.getInstance().getDisplay().setDisplayOrigin(
                position.get(Axis2D.X).intValue() - Game.getInstance().getDisplayWidth() / 2,
                position.get(Axis2D.Y).intValue() - Game.getInstance().getDisplayHeight() / 2
        );

        System.out.println(position);
    }

    private void renderClouds() {
        // player's position
        int x = position.get(Axis2D.X).intValue();
        int y = position.get(Axis2D.Y).intValue();

        // top left corner of the chunk that the player is currently in
        int currentChunkX = x - (x % screenWidth);
        int currentChunkY = y - (y % screenHeight);

        if (x < 0) {
            currentChunkX -= screenWidth;
        }

        if (y < 0) {
            currentChunkY -= screenHeight;
        }

        if (lastChunkX != currentChunkX || lastChunkY != currentChunkY) {
            Chunk[] surroundingChunks = {
                new Chunk(currentChunkX - screenWidth, currentChunkY - screenHeight),
                new Chunk(currentChunkX, currentChunkY - screenHeight),
                new Chunk(currentChunkX + screenWidth, currentChunkY - screenHeight),
                new Chunk(currentChunkX + screenWidth, currentChunkY),
                new Chunk(currentChunkX + screenWidth, currentChunkY + screenHeight),
                new Chunk(currentChunkX, currentChunkY + screenHeight),
                new Chunk(currentChunkX - screenWidth, currentChunkY + screenHeight),
                new Chunk(currentChunkX - screenWidth, currentChunkY)
            };

            for (Chunk chunk : surroundingChunks) {
                if (!cloudedChunks.contains(chunk)) {
                    int coordinates[] = chunk.getCoordinates();
                    new CloudChunk(coordinates[0], coordinates[0] + screenWidth, coordinates[1], coordinates[1] + screenHeight);             
                    cloudedChunks.add(chunk);
                }
            }

            lastChunkX = currentChunkX;
            lastChunkY = currentChunkY;
        }
    }

    @Override
    public void update() {
        // rotating the player
        rotateToCursor();

        // System.out.println(position.get(Axis2D.X).intValue() % screenWidth);
        // moving the player
        move();

        // rendering clouds
        renderClouds();
    }

    @Override
    public DepthLayer getDepth() {
        return DepthLayer.FOREGROUND;
    }
}
