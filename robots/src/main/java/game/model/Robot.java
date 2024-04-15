package game.model;


import java.awt.*;
import java.beans.PropertyChangeEvent;
import game.model.MathUtils;
import game.model.GameModel;

import static game.model.MathUtils.calculateAngularVelocity;

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

    private void moveRobot(double velocity, double angularVelocity, double duration) {
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
        setPositionX(MathUtils.normalize(newX, 0, GameModel.getModelSettings().getDimension().width));
        setPositionY(MathUtils.normalize(newY, 0, GameModel.getModelSettings().getDimension().height));
        double newDirection = MathUtils.asNormalizedRadians(getRobotDirection() + angularVelocity * duration);
        setRobotDirection(newDirection);
    }

    @Override
    public void update() {
        double distance = MathUtils.distance(target.p().x, target.p().y,
                getPositionX(), getPositionY());
        if (distance < 0.5) {
            return;
        }
        double velocity = Robot.maxVelocity;
        double angleToTarget = MathUtils.angleTo(getPositionX(), getPositionY(),
                target.p().x, target.p().y);
//        double angularVelocity = 0;
//        if (angleToTarget > getRobotDirection()) {
//            angularVelocity = Robot.maxAngularVelocity;
//        }
//        if (angleToTarget < getRobotDirection()) {
//            angularVelocity = -Robot.maxAngularVelocity;
//        }

        double angularVelocity = MathUtils.calculateAngularVelocity(angleToTarget, getRobotDirection(), Robot.maxAngularVelocity);

        moveRobot(velocity, angularVelocity, 10);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("New Point")) {
            Point point = (Point) evt.getNewValue();
            this.target = new Target(new Point(point.x * 2, point.y * 2));
        }
    }
}