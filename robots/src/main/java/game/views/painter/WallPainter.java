package game.views.painter;

import game.model.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class WallPainter extends Painter<Wall> {
    @Override
    public void paint(Graphics2D graphics, Wall drawable, PaintingContext context) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        graphics.setTransform(t);
        graphics.setColor(Color.GRAY);
        fillRectangle(graphics, drawable.point().x, drawable.point().y, drawable.width(), drawable.height());
        graphics.setColor(Color.BLACK);
        drawRectangle(graphics, drawable.point().x, drawable.point().y, drawable.width(), drawable.height());
    }
}
