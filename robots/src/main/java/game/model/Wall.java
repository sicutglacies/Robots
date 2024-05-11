package game.model;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static game.model.MathUtils.getRandomNumber;
import static game.model.MathUtils.getRandomPoint;


public class Wall implements Entity {
    public static Dimension maxDimension = new Dimension(800, 800);
    public static int wallCountMin = 16;
    public static int wallCountMax = 100;
    public static int minBorderX = 0;
    public static int minBorderY = 0;
    private final int width;
    private final int height;
    private final Point point;

    public Wall(Point point, int width, int height) {
        this.point = point;
        this.width = width;
        this.height = height;
    }

    public static List<Wall> generateWalls() {
        Dimension dimension = GameModel.getModelSettings().getDimension();

        if (dimension.width - maxDimension.width > 50 || dimension.height - maxDimension.height > 50) {
            wallCountMin = (dimension.width * dimension.height - maxDimension.width * maxDimension.height) / (16 * 2500);
            wallCountMax = (dimension.width * dimension.height - maxDimension.width * maxDimension.height) / (16 * 400);
            minBorderX = maxDimension.width;
            minBorderY = maxDimension.height;
            maxDimension = dimension;
        }

        List<Wall> walls = generateWallsParameters(dimension);
        wallCountMin = 0;
        wallCountMax = 0;
        return walls;
    }

    private static List<Wall> generateWallsParameters(Dimension dimension) {
        List<Wall> walls = new ArrayList<>();
        int wallCount = getRandomNumber(wallCountMin, wallCountMax);

        for (int i = 0; i < wallCount; i++) {
            int wallWidth = getRandomNumber(20, 50);
            int wallHeight = getRandomNumber(20, 50);
            Point p = getRandomPoint(minBorderX, minBorderY, dimension.width-wallWidth, dimension.height-wallHeight);

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

    public Point getPoint() {
        return point;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
