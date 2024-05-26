package game.views.painter;

import game.model.Enemy;

import java.awt.*;
import java.awt.geom.AffineTransform;
public class EnemyPainter extends Painter<Enemy> {
    @Override
    public void paint(Graphics2D graphics, Enemy drawable, PaintingContext context) {
        int enemyCenterX = (int) Math.round(drawable.getPositionX());
        int enemyCenterY = (int) Math.round(drawable.getPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(drawable.getDirection(), enemyCenterX, enemyCenterY);
        graphics.setTransform(t);
        graphics.setColor(Color.BLACK);
        fillOval(graphics, enemyCenterX - 15, enemyCenterY, 30, 10);
        graphics.setColor(Color.BLACK);
        drawOval(graphics, enemyCenterX - 15, enemyCenterY, 30, 10);
        graphics.setColor(Color.WHITE);
        fillOval(graphics, enemyCenterX - 5, enemyCenterY, 5, 5);
        graphics.setColor(Color.BLACK);
        drawOval(graphics, enemyCenterX - 5, enemyCenterY, 5, 5);
    }
}