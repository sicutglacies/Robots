package game.model;

import java.awt.geom.Point2D;

public abstract class Robot {
    protected double positionX;
    protected double positionY;
    public final double maxVelocity = 0.1;
    public final double maxAngularVelocity = 0.01;
    protected volatile double direction;
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

    public double getDirection() {
        return direction;
    }
    public void setDirection(double direction) {
        this.direction = direction;
    }

    protected Point2D.Double getAllowedStep (
            double currX, double currY, double stepX, double stepY, java.util.List<Wall> walls) {

        for (Wall wall: walls) {
            // Get wall rectangle coordinates
            double xMin = wall.point().x;
            double yMin = wall.point().y;
            double xMax = xMin + wall.width();
            double yMax = yMin + wall.height();

            // If robot step is on wall, then return the closest point on the wall border
            if (stepX >= xMin && stepX <= xMax && stepY >= yMin && stepY <= yMax) {
                stepX = Math.max(xMin, Math.min(currX, xMax));
                stepY = Math.max(yMin, Math.min(currY, yMax));
            }
        }
        return new Point2D.Double(stepX, stepY);
    }

    protected Point2D.Double calcStep(double velocity, double angularVelocity, double duration) {
        velocity = MathUtils.applyLimits(velocity, 0, maxVelocity);
        angularVelocity = MathUtils.applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = getPositionX() + velocity / angularVelocity *
                (Math.sin(getDirection() + angularVelocity * duration) -
                        Math.sin(getDirection()));
        if (!Double.isFinite(newX)) {
            newX = getPositionX() + velocity * duration * Math.cos(getDirection());
        }
        double newY = getPositionY() - velocity / angularVelocity *
                (Math.cos(getDirection() + angularVelocity * duration) -
                        Math.cos(getDirection()));
        if (!Double.isFinite(newY)) {
            newY = getPositionY() + velocity * duration * Math.sin(getDirection());
        }

        newX = MathUtils.normalize(newX, 0, GameModel.getModelSettings().getDimension().width);
        newY = MathUtils.normalize(newY, 0, GameModel.getModelSettings().getDimension().height);

        return new Point2D.Double(newX, newY);
    }
}
