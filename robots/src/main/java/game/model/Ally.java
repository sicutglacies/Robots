package game.model;


import java.awt.*;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;


public class Ally extends Robot implements Entity {
    private final PropertyChangeSupport pclSupport;
    private Target target;

    public Ally() {
        this.pclSupport = new PropertyChangeSupport(this);
        this.positionX = 100;
        this.positionY = 100;
        this.target = new Target(new Point(100, 150));
        this.direction = 0;
    }
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pclSupport.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) { pclSupport.removePropertyChangeListener(pcl); }

    public Target getTarget() {
        return target;
    }

    private void move(double velocity, double angularVelocity, double duration, List<Wall> walls) {
        Point2D.Double newStep = calcStep(velocity, angularVelocity, duration);
        Point2D.Double allowedStep = getAllowedStep(getPositionX(), getPositionY(), newStep.x, newStep.y, walls);

        setPositionX(allowedStep.x);
        setPositionY(allowedStep.y);
        double newDirection = MathUtils.asNormalizedRadians(getDirection() + angularVelocity * duration);
        setDirection(newDirection);
    }

    @Override
    public void update(ModelContext modelContext) {
        double distance = MathUtils.distance(target.p().x, target.p().y,
                getPositionX(), getPositionY());
        if (distance < 0.5) {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = MathUtils.angleTo(getPositionX(), getPositionY(),
                target.p().x, target.p().y);

        double angularVelocity = MathUtils.calculateAngularVelocity(angleToTarget, getDirection(), maxAngularVelocity);

        double oldPositionX = getPositionX();
        double oldPositionY = getPositionY();

        move(velocity, angularVelocity, 10, modelContext.findEntities(Wall.class));
        pclSupport.firePropertyChange("New Position",
                new Point(
                        (int) Math.round(oldPositionX),
                        (int) Math.round(oldPositionY)
                ),
                new Point(
                        (int) Math.round(getPositionX()),
                        (int) Math.round(getPositionY())
                )
        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("New Point")) {
            Point point = (Point) evt.getNewValue();
            this.target = new Target(new Point(point.x * 2, point.y * 2));
        }
    }
}