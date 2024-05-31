package game.model;


import java.awt.*;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class Enemy extends Robot implements Entity  {
    private final PropertyChangeSupport pclSupport;
    private Point target;
    public final double maxVelocity = 0.075;

    public Enemy() {
        this.pclSupport = new PropertyChangeSupport(this);
        this.positionX = MathUtils.getRandomNumber(400, 800);
        this.positionY = MathUtils.getRandomNumber(400, 800);
        this.target = new Point(100, 100);
        this.direction = 0;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pclSupport.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) { pclSupport.removePropertyChangeListener(pcl); }

    private void move(double velocity, double angularVelocity, double duration, List<Wall> walls) {
        Point2D.Double newStep = calcStep(velocity, angularVelocity, duration);
        Point2D.Double allowedStep = getAllowedStep(getPositionX(), getPositionY(), newStep.x, newStep.y, walls);

        setPositionX(allowedStep.x);
        setPositionY(allowedStep.y);

        double newDirection = MathUtils.asNormalizedRadians(getDirection() + angularVelocity * duration);

        if (newStep.x != allowedStep.x || newStep.y != allowedStep.y) {
            newDirection = MathUtils.asNormalizedRadians(
                    getDirection() + MathUtils.getRandomNumber(0, Math.PI) + angularVelocity * duration);
        }
        setDirection(newDirection);
    }

    @Override
    public void update(ModelContext modelContext) {
        double distance = MathUtils.distance(target.x, target.y,
                getPositionX(), getPositionY());
        if (distance < 0.7) {
            pclSupport.firePropertyChange("Game Over", null, 1);
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = MathUtils.angleTo(getPositionX(), getPositionY(),
                target.x, target.y);

        double angularVelocity = MathUtils.calculateAngularVelocity(angleToTarget, getDirection(), maxAngularVelocity);

        move(velocity, angularVelocity, 10, modelContext.findEntities(Wall.class));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("New Position")) {
            Point point = (Point) evt.getNewValue();
            this.target = new Point(point.x, point.y);
        }
    }
}
