package game.views.painter;

import game.model.Ally;

import java.awt.*;
import java.awt.geom.AffineTransform;
public class AllyPainter extends Painter<Ally> {
    @Override
    public void paint(Graphics2D graphics, Ally drawable, PaintingContext context) {
        int allyCenterX = (int) Math.round(drawable.getPositionX());
        int allyCenterY = (int) Math.round(drawable.getPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(drawable.getDirection(), allyCenterX, allyCenterY);
        graphics.setTransform(t);
        graphics.setColor(Color.MAGENTA);
        fillOval(graphics, allyCenterX - 15, allyCenterY, 30, 10);
        graphics.setColor(Color.BLACK);
        drawOval(graphics, allyCenterX - 15, allyCenterY, 30, 10);
        graphics.setColor(Color.WHITE);
        fillOval(graphics, allyCenterX - 5, allyCenterY, 5, 5);
        graphics.setColor(Color.BLACK);
        drawOval(graphics, allyCenterX - 5, allyCenterY, 5, 5);

        context.paint(graphics, drawable.getTarget());
    }
}