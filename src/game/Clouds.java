package game;

import engine.Game;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;
import java.util.ArrayList;
import java.util.List;

public class Clouds implements Updateable {

    private final List<SpriteSheet> clouds = new ArrayList<>();
    private final Player player;

    public Clouds(Player player) {
        this.player = player;

        for (int i = 0; i < 20; i++) {
            int frame = (int) (Math.min(Math.random(), Math.random()) * 3);
            SpriteSheet spriteSheet = new SpriteSheet("clouds.png", 45, 15,
                    (float) Math.random() * Game.getInstance().getDisplayWidth(),
                    (float) Math.random() * Game.getInstance().getDisplayHeight()
            ) {
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
        int height = Game.getInstance().getDisplayWidth();

        clouds.forEach(cloud -> {

            Vector2D direction = player.getVelocity();
            direction.scale((float) (1 - cloud.getFrame()) / 2);

            cloud.move(direction);

            float cloudWidth = cloud.getWidth();

            if (cloud.getX() < player.getX() - (width / 2) - cloudWidth) {
                cloud.move(width + cloudWidth * 2, 0);
            } else if (cloud.getX() > player.getX() + (width / 2) + cloudWidth) {
                cloud.move(-width - cloudWidth * 2, 0);
            }

            if (cloud.getY() < player.getY() - height / 2) {
                cloud.move(0, height);
            } else if (cloud.getY() > player.getY() + height / 2) {
                cloud.move(0, -height);
            }
        });
    }
}
