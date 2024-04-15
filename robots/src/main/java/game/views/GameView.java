package game.views;

import game.model.Entity;
import game.viewmodels.EntitiesProvider;
import game.views.painter.PaintingContext;

import javax.swing.*;
import java.awt.*;


public class GameView extends JPanel {
    private final EntitiesProvider entitiesProvider;
    private final PaintingContext paintingContext ;

    public GameView(EntitiesProvider entitiesProvider) {
        this.entitiesProvider = entitiesProvider;
        this.paintingContext = new PaintingContext();
        setDoubleBuffered(true);
    }

    public void updateView() {
        onRedrawEvent();
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Entity entity : entitiesProvider.getEntities()) {
            paintingContext.paint(g2d, entity);
        }
    }
}