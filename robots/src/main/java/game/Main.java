package game;

import game.viewmodels.EntitiesProvider;
import game.viewmodels.SimpleEntityProvider;
import game.views.GameView;
import game.views.GameWindow;
import game.model.GameModel;
import game.viewmodels.GameViewModel;

import javax.swing.*;
import java.awt.*;

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
        GameViewModel viewModel = new GameViewModel(gameModel, gameWindow);

        SwingUtilities.invokeLater(() -> {
            game.MainApplicationFrame frame = new MainApplicationFrame(viewModel);
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}