package game;

import engine.Game;
import engine.math.Vector2D;
import engine.render.SpriteSheet;
import engine.update.Updateable;
import java.util.ArrayList;
import java.util.List;

public class Clouds implements Updateable {

    private List<SpriteSheet> clouds = new ArrayList<>();
    private Player player;

    public Clouds(Player player) {
        this.player = player;

        for (int i = 0; i < 100; i++) {
            int frame = (int) (Math.random() * 3);
            var a = new SpriteSheet("clouds.png", 45, 15,
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

            a.setFrame(frame);
            clouds.add(
                    a
            );
        }

        registerUpdate();
    }

    @Override
    public void update() {
        int width = Game.getInstance().getDisplayWidth();
        int height = Game.getInstance().getDisplayWidth();

        clouds.forEach(e -> {

            Vector2D d = player.getVelocity();
            d.scale((float) (1 - e.getFrame()) / 2);

            e.move(d);
//            e.move(player.getV);

            if (e.getX() < player.getX() - width / 2) {
                e.move(width, 0);
            } else if (e.getX() > player.getX() + width / 2) {
                e.move(-width, 0);
            }

            if (e.getY() < player.getY() - height / 2) {
                e.move(0, height);
            } else if (e.getY() > player.getY() + height / 2) {
                e.move(0, -height);
            }
        });
    }
}
