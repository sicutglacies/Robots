package game.viewmodels;

import game.log.LogWindow;
import game.views.GameView;
import game.views.GameWindow;
import game.model.GameModel;
import game.views.locale.Localizer;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;


public class GameViewModel {
    private final GameModel gameModel;
    private final GameWindow gameWindow;
    private final LogWindow logWindow;
    private final java.util.Timer timer = initTimer();

    private static java.util.Timer initTimer() {
        return new Timer("events generator", true);
    }

    public GameViewModel(GameModel gameModel, GameWindow gameWindow, LogWindow logWindow) {
        this.gameModel = gameModel;
        this.gameWindow = gameWindow;
        this.logWindow = logWindow;
        initGameListeners();
    }

    private void initGameListeners() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GameModel.initModelSettings(gameWindow.getSize());
                getGameView().updateView();
            }
        }, 0, 50);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.updateModel();
            }
        }, 0, 10);
        gameWindow.getGameView().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameModel.setTarget(e.getPoint());
                getGameView().repaint();
            }
        });
        gameWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                super.componentResized(e);
                GameModel.initModelSettings(gameWindow.getSize());
                gameModel.addWalls();
            }
        });

        Localizer.registerListener(gameWindow);
        Localizer.registerListener(logWindow);
    }

    public GameView getGameView() {
        return gameWindow.getGameView();
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public LogWindow getLogWindow() {
        return logWindow;
    }
}