package game.model;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import static game.model.MathUtils.getRandomNumber;


public class Wall implements Entity {
    public static int wallCountMin = 10;
    public static int wallCountMax = 30;
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

    public static ArrayList<Wall> generateWalls() {
        Dimension dimension = GameModel.getModelSettings().getDimension();
        ArrayList<Wall> walls = new ArrayList<>();

        for (int i = 0; i < getRandomNumber(wallCountMin, wallCountMax); i++){
            int wallWidth = getRandomNumber(20, 50);
            int wallHeight = getRandomNumber(20, 50);
            int randomX = getRandomNumber(minBorderX, dimension.width-wallWidth);
            int randomY = getRandomNumber(minBorderY, dimension.height-wallHeight);

            walls.add(new Wall(new Point(randomX, randomY), wallWidth, wallHeight));
        }
        Robot.walls = walls;

        return walls;
    }

    @Override
    public void update() {

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
