package game.views;


import game.views.locale.LocaleChangeListener;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

import static game.MainApplicationFrame.bundle;

public class GameWindow extends JInternalFrame implements LocaleChangeListener {
    private final GameView gameView;

    public GameWindow(GameView gameView) {
        super(bundle.getString("gameField"), true, true, true, true);
        this.gameView = gameView;
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(this.gameView, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        setSize(400, 400);
    }

    public GameView getGameView() {
        return this.gameView;
    }

    @Override
    public void onLocaleChanged(ResourceBundle bundle) {
        this.title = bundle.getString("gameField");
        this.repaint();
    }
}
