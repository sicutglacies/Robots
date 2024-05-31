package game.model;

import game.log.Logger;

import java.awt.*;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameModel implements PropertyChangeListener {
    private static ModelSettings modelSettings = new ModelSettings(new Dimension(400, 400));
    private final List<Entity> entities = new CopyOnWriteArrayList<>();
    private final PropertyChangeSupport pclSupport;
    private final ModelContext modelContext = new ModelContext(this);

    public GameModel() {
        this.pclSupport = new PropertyChangeSupport(this);
        this.initGameField();
    }

    private void addEntity(Entity entity) {
        addPropertyChangeListener(entity);
        this.entities.add(entity);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pclSupport.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) { pclSupport.removePropertyChangeListener(pcl); }

    public void updateModel() {
        for (Entity entity : entities)
            entity.update(modelContext);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addWalls() {
        List<Point2D.Double> positions = new ArrayList<>();
        for (Entity entity : getEntities()) {
            if (entity.getClass() != Wall.class) {
                Robot robot = (Robot) entity;
                positions.add(new Point2D.Double(robot.positionX, robot.positionY));
            }
        };

        for (Entity wall : Wall.generateWalls(positions)) {
            addEntity(wall);
        }
    }

    private void initGameField() {
        Ally ally = new Ally();
        this.addEntity(ally);
        for (int i = 0; i < 3; i++) {
            Enemy enemy = new Enemy();
            enemy.addPropertyChangeListener(this);
            ally.addPropertyChangeListener(enemy);
            this.addEntity(enemy);
        }
        this.addWalls();
    }

    public void setTarget(Point point) {
        pclSupport.firePropertyChange("New Point", null, point);
    }

    public static void initModelSettings(Dimension dimension) {
        modelSettings = new ModelSettings(dimension);
    }

    public static ModelSettings getModelSettings() {
        return modelSettings;
    }

    public static class ModelSettings {
        private final Dimension dimension;
        private ModelSettings(Dimension dimension) {
            this.dimension = new Dimension(dimension.width * 2, dimension.height * 2);
        }
        public Dimension getDimension() {
            return dimension;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("Game Over")) {
            entities.clear();
            Logger.debug("Game Over");
        }
    }
}