package game.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static game.model.MathUtils.*;


public record Wall(Point point, int width, int height) implements Entity {
    public static Dimension maxDimension = new Dimension(800, 800);
    public static int wallCountMin = 16;
    public static int wallCountMax = 32;
    public static int minBorderX = 0;
    public static int minBorderY = 0;

    public static List<Wall> generateWalls(List<Point2D.Double> positions) {
        Dimension dimension = GameModel.getModelSettings().getDimension();

        if (MathUtils.distance(dimension.width, dimension.height, maxDimension.width, maxDimension.height) > 70) {
            wallCountMin = (dimension.width * dimension.height - maxDimension.width * maxDimension.height) / (32 * 2500);
            wallCountMax = (dimension.width * dimension.height - maxDimension.width * maxDimension.height) / (32 * 400);
            minBorderX = maxDimension.width;
            minBorderY = maxDimension.height;
            maxDimension = dimension;
        }

        List<Wall> walls = generateWallsParameters(dimension, positions);
        wallCountMin = 0;
        wallCountMax = 0;
        return walls;
    }

    private static List<Wall> generateWallsParameters(Dimension dimension, List<Point2D.Double> positions) {
        List<Wall> walls = new ArrayList<>();
        int wallCount = getRandomIntNumber(wallCountMin, wallCountMax);

        for (int i = 0; i < wallCount; i++) {
            int wallWidth = getRandomIntNumber(20, 50);
            int wallHeight = getRandomIntNumber(20, 50);
            Point p = getRandomPoint(minBorderX, minBorderY, dimension.width - wallWidth, dimension.height - wallHeight, positions);

            walls.add(new Wall(p, wallWidth, wallHeight));
        }
        return walls;
    }

    @Override
    public void update(ModelContext modelContext) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
