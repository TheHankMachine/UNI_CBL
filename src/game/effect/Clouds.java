package game.effect;

import engine.Game;
import engine.math.Axis2D;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;
import java.util.ArrayList;
import java.util.List;

public class Clouds implements Updateable {

    private final List<SpriteSheet> clouds = new ArrayList<>();
    private final Vector2D lastCameraPosition = new Vector2D();

    public Clouds() {
        for (int i = 0; i < 20; i++) {
            int frame = (int) (Math.min(Math.random(), Math.random()) * 3);
            SpriteSheet spriteSheet = new SpriteSheet("clouds.png", 45, 15,
                    (float) Math.random() * Game.getInstance().getDisplayWidth() * 2,
                    (float) Math.random() * Game.getInstance().getDisplayHeight() * 2
            ) {
                @Override
                public DepthLayer getDepth() {
                    return List.of(
                            DepthLayer.BACKGROUND,
                            DepthLayer.MIDDLEGROUND,
                            DepthLayer.FOREGROUND
                    ).get(frame);
                }
            };

            spriteSheet.setFrame(frame);
            clouds.add(spriteSheet);
        }

        registerUpdate();
    }

    public void destroyClouds() {
        clouds.forEach((cloud) -> cloud.deregisterRender());
        deregisterUpdate();
    }

    @Override
    public void update() {
        int width = Game.getInstance().getDisplayWidth();
        int height = Game.getInstance().getDisplayHeight();

        Vector2D delta = Game.getInstance().getDisplay().getDisplayOrigin().copy();
        delta.subtract(lastCameraPosition);

        clouds.forEach((cloud) -> {
            float relativeX = cloud.getX() - Game.getInstance().getDisplay().getDisplayOriginX();
            float relativeY = cloud.getY() - Game.getInstance().getDisplay().getDisplayOriginY();

            Vector2D direction = delta.copy();
            direction.scale((float) (1 - cloud.getFrame()) / 2);

            cloud.move(direction);

            if (relativeX + cloud.getWidth() / 2 < 0) {
                cloud.move(Axis2D.X, width + cloud.getWidth());
            } else if (relativeX - cloud.getWidth() / 2 > width) {
                cloud.move(Axis2D.X, -width - cloud.getWidth());
            }

            if (relativeY + cloud.getHeight() / 2 < 0) {
                cloud.move(Axis2D.Y, height + cloud.getHeight());
            } else if (relativeY - cloud.getHeight() / 2 > height)  {
                cloud.move(Axis2D.Y, -height - cloud.getHeight());
            }
        });

        lastCameraPosition.setTo(Game.getInstance().getDisplay().getDisplayOrigin());
    }
}
