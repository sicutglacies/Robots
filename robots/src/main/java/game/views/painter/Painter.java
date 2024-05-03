package game.views.painter;

import java.awt.*;

public abstract class Painter<D> {
    public abstract void paint(Graphics2D graphics, D drawable, PaintingContext context);
    protected static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    protected static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    protected static void fillRectangle(Graphics g, int PosX, int PosY, int width, int height) {
        g.fillRect(PosX, PosY, width, height);
    }
    protected static void drawRectangle(Graphics g, int PosX, int PosY, int width, int height) {
        g.drawRect(PosX, PosY, width, height);
    }
}