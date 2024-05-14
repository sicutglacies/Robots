package game.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

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

    public static double calculateAngularVelocity(double angle, double robotDirection, double maxAngularVelocity) {
        double diff = angle - robotDirection;
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

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static Point getRandomPoint(int xMin, int yMin, int xMax, int yMax) {
        int randomX = getRandomNumber(0, xMax);
        int randomY = getRandomNumber(0, yMax);

        while (randomX >= 0 && randomX <= xMin && randomY >= 0 & randomY <= yMin) {
            randomX = getRandomNumber(0, xMax);
            randomY = getRandomNumber(0, yMax);
        }
        return new Point(randomX, randomY);
    }
}
