package game;

import engine.input.Clickable;
import engine.render.Sprite;
import java.awt.event.MouseEvent;

public class Button extends Sprite implements Clickable{

    private final Text text;

    public Button(String textContent, float x, float y) {
        super("button.png", x, y);
        text = new Text(textContent, x, y);

        addClickListener();
    }

    @Override
    public void onClick(MouseEvent e) {}

    @Override
    public DepthLayer getDepth() {
        return DepthLayer.UI;
    }

    @Override
    public void deregisterRender() {
        super.deregisterRender();
        text.deregisterRender();
    }
}
