package game.model;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private static ModelSettings modelSettings = new ModelSettings(new Dimension(400, 400));
    private final List<Entity> entities = new ArrayList<>();
    private final PropertyChangeSupport pclSupport;

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

    public void removePropertyChangeListener(PropertyChangeListener pcl) { pclSupport.removePropertyChangeListener(pcl);
    }

    public void updateModel() {
        for (Entity entity : entities)
            entity.update(new ModelContext(this));
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addWalls() {
        for (Entity wall : Wall.generateWalls()) {
            addEntity(wall);
        }
    }

    public void initGameField() {
        Robot robot = new Robot();
        this.addEntity(robot);
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
}