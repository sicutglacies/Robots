package game;

import game.log.LogWindow;
import game.log.Logger;
import game.viewmodels.EntitiesProvider;
import game.viewmodels.SimpleEntityProvider;
import game.views.GameView;
import game.views.GameWindow;
import game.model.GameModel;
import game.viewmodels.GameViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        GameModel gameModel = new GameModel();
        EntitiesProvider entitiesProvider = new SimpleEntityProvider(gameModel);
        GameView gameView = new GameView(entitiesProvider);
        GameWindow gameWindow = new GameWindow(gameView);
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        GameViewModel viewModel = new GameViewModel(gameModel, gameWindow, logWindow);

        SwingUtilities.invokeLater(() -> {
            game.MainApplicationFrame frame = new MainApplicationFrame(viewModel);
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}