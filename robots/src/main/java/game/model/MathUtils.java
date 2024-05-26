package game.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class MathUtils {
    public static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public static double asNormalizedRadians(double angle) {
        angle = angle % (2 * Math.PI);
        if (angle < -Math.PI) {
            angle += 2 * Math.PI;
        } else if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    public static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    public static double calculateAngularVelocity(double angle, double direction, double maxAngularVelocity) {
        double diff = angle - direction;
        diff = asNormalizedRadians(diff);

        return Math.signum(diff) * Math.min(maxAngularVelocity, Math.abs(diff));
    }

    public static double normalize(double value, double minValue, double maxValue) {
        if (value < minValue)
            return minValue;
        return Math.min(value, maxValue);
    }

    public static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    private static boolean OnRobot(int randomX, int randomY, List<Point2D.Double> positions) {
        for (Point2D.Double position : positions) {
            if (Math.abs(randomX - position.x) < 50 && Math.abs(randomY - position.y) < 50)
                return false;
        }
        return true;
    }

    public static int getRandomIntNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static double getRandomNumber(double min, double max) { return ((Math.random() * (max - min)) + min); }

    public static Point getRandomPoint(int xMin, int yMin, int xMax, int yMax, List<Point2D.Double> positions) {
        int randomX = getRandomIntNumber(0, xMax);
        int randomY = getRandomIntNumber(0, yMax);

        while ((randomX < xMin && randomY < yMin) || !OnRobot(randomX, randomY, positions)) {
            randomX = getRandomIntNumber(0, xMax);
            randomY = getRandomIntNumber(0, yMax);
        }
        return new Point(randomX, randomY);
    }
}
