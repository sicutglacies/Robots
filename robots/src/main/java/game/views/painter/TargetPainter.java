package game.views.painter;

import game.model.Target;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class TargetPainter extends Painter<Target> {

    @Override
    public void paint(Graphics2D graphics, Target drawable, PaintingContext context) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        graphics.setTransform(t);
        graphics.setColor(Color.GREEN);
        fillOval(graphics, drawable.p().x, drawable.p().y, 5, 5);
        graphics.setColor(Color.BLACK);
        drawOval(graphics, drawable.p().x, drawable.p().y, 5, 5);
    }
}