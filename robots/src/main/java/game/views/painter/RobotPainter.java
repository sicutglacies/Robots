package game.views.painter;

import game.model.Robot;

import java.awt.*;
import java.awt.geom.AffineTransform;
public class RobotPainter extends Painter<Robot> {
    @Override
    public void paint(Graphics2D graphics, Robot drawable, PaintingContext context) {
        int robotCenterX = (int) Math.round(drawable.getPositionX());
        int robotCenterY = (int) Math.round(drawable.getPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(drawable.getRobotDirection(), robotCenterX, robotCenterY);
        graphics.setTransform(t);
        graphics.setColor(Color.MAGENTA);
        fillOval(graphics, robotCenterX, robotCenterY, 30, 10);
        graphics.setColor(Color.BLACK);
        drawOval(graphics, robotCenterX, robotCenterY, 30, 10);
        graphics.setColor(Color.WHITE);
        fillOval(graphics, robotCenterX  + 10, robotCenterY, 5, 5);
        graphics.setColor(Color.BLACK);
        drawOval(graphics, robotCenterX  + 10, robotCenterY, 5, 5);

        context.paint(graphics, drawable.getTarget());
    }
}