package game.model;


import java.awt.*;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;


public class Robot implements Entity {
    private double positionX;
    private double positionY;
    private Target target;
    public static final double maxVelocity = 0.1;
    public static final double maxAngularVelocity = 0.01;

    private volatile double robotDirection;

    public Robot() {
        this.positionX = 100;
        this.positionY = 100;
        this.target = new Target(new Point(100, 150));
        this.robotDirection = 0;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getRobotDirection() {
        return robotDirection;
    }

    public void setRobotDirection(double robotDirection) {
        this.robotDirection = robotDirection;
    }

    public Target getTarget() {
        return target;
    }

    private Point2D.Double getAllowedStep (
            double currX, double currY, double stepX, double stepY, List<Wall> walls) {

        for (Wall wall: walls) {
            // Get wall rectangle coordinates
            double xMin = wall.getPoint().x;
            double yMin = wall.getPoint().y;
            double xMax = xMin + wall.getWidth();
            double yMax = yMin + wall.getHeight();

            // If robot step is on wall, then return the closest point on the wall border
            if (stepX >= xMin && stepX <= xMax && stepY >= yMin && stepY <= yMax) {
                stepX = Math.max(xMin, Math.min(currX, xMax));
                stepY = Math.max(yMin, Math.min(currY, yMax));
            }
        }
        return new Point2D.Double(stepX, stepY);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration, List<Wall> walls) {
        velocity = MathUtils.applyLimits(velocity, 0, Robot.maxVelocity);
        angularVelocity = MathUtils.applyLimits(angularVelocity, -Robot.maxAngularVelocity, Robot.maxAngularVelocity);
        double newX = getPositionX() + velocity / angularVelocity *
                (Math.sin(getRobotDirection() + angularVelocity * duration) -
                        Math.sin(getRobotDirection()));
        if (!Double.isFinite(newX)) {
            newX = getPositionX() + velocity * duration * Math.cos(getRobotDirection());
        }
        double newY = getPositionY() - velocity / angularVelocity *
                (Math.cos(getRobotDirection() + angularVelocity * duration) -
                        Math.cos(getRobotDirection()));
        if (!Double.isFinite(newY)) {
            newY = getPositionY() + velocity * duration * Math.sin(getRobotDirection());
        }

        newX = MathUtils.normalize(newX, 0, GameModel.getModelSettings().getDimension().width);
        newY = MathUtils.normalize(newY, 0, GameModel.getModelSettings().getDimension().height);

        Point2D.Double allowedStep = getAllowedStep(getPositionX(), getPositionY(), newX, newY, walls);

        setPositionX(allowedStep.x);
        setPositionY(allowedStep.y);
        double newDirection = MathUtils.asNormalizedRadians(getRobotDirection() + angularVelocity * duration);
        setRobotDirection(newDirection);
    }

    @Override
    public void update(ModelContext modelContext) {
        double distance = MathUtils.distance(target.p().x, target.p().y,
                getPositionX(), getPositionY());
        if (distance < 0.5) {
            return;
        }
        double velocity = Robot.maxVelocity;
        double angleToTarget = MathUtils.angleTo(getPositionX(), getPositionY(),
                target.p().x, target.p().y);

        double angularVelocity = MathUtils.calculateAngularVelocity(angleToTarget, getRobotDirection(), Robot.maxAngularVelocity);

        moveRobot(velocity, angularVelocity, 10, modelContext.findEntities(Wall.class));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("New Point")) {
            Point point = (Point) evt.getNewValue();
            this.target = new Target(new Point(point.x * 2, point.y * 2));
        }
    }
}