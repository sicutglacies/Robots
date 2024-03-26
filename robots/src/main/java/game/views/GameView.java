package game.views;

import game.model.GameModel;
import game.model.Robot;
import game.views.painter.RobotPainter;
import game.views.painter.TargetPainter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JPanel {
    private final GameModel gameModel;
    RobotPainter robotDrawer;
    TargetPainter targetDrawer;

    public GameView(GameModel gameModel) {
        robotDrawer = new RobotPainter();
        targetDrawer = new TargetPainter();
        this.gameModel = gameModel;
        setDoubleBuffered(true);
    }

    public void updateView() {
        onRedrawEvent();
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        ArrayList<Robot> robots = (ArrayList<Robot>) gameModel.getRobots();
        for (Robot robot : robots) {
            robotDrawer.drawRobot(g2d, robot);
            targetDrawer.drawTarget(g2d, robot.getTarget());
        }
    }
}