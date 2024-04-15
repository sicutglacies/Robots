package game.model;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private static ModelSettings modelSettings;
    private final List<Entity> entities = new ArrayList<>();
    private PropertyChangeSupport pclSupport;

    public GameModel() {
        this.pclSupport = new PropertyChangeSupport(this);

        entities.add(new Robot());
        for (Entity robot : entities)
            addPropertyChangeListener(robot);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pclSupport.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) { pclSupport.removePropertyChangeListener(pcl);
    }

    public void updateModel() {
        for (Entity robot : entities)
            robot.update();
    }

    public List<Entity> getEntities() {
        return entities;
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