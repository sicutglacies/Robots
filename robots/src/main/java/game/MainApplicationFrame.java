package game;


import game.log.Logger;
import game.log.LogWindow;

import game.views.GameWindow;
import game.views.locale.Localizer;
import game.views.locale.MenuItem;

import game.viewmodels.GameViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;
import static game.views.locale.Localizer.bundle;


public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationFrame(GameViewModel gameViewModel) {

        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        LogWindow logWindow = gameViewModel.getLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = gameViewModel.getGameWindow();
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(generateLookAndFeelMenu().getItem());
        menuBar.add(generateTestMenu().getItem());
        menuBar.add(generateLangMenu().getItem());
        return menuBar;
    }

    private void addMenuItem(MenuItem menu, String label, ActionListener itemListener) {
        MenuItem item = new MenuItem(new JMenuItem(), label);
        Localizer.registerListener(item);
        item.getItem().addActionListener(itemListener);
        menu.getItem().add(item.getItem());
    }

    private void addMenuItem(MenuItem menu, MenuItem item, ActionListener itemListener) {
        item.getItem().addActionListener(itemListener);
        menu.getItem().add(item.getItem());
    }

    private MenuItem generateLangMenu(){
        MenuItem langMenu =
                new MenuItem(new JMenu(bundle.getString("lang")), "lang");

        langMenu.getItem().setMnemonic(KeyEvent.VK_C);
        langMenu.getItem().getAccessibleContext().setAccessibleDescription("Выбрать язык");

        MenuItem russian = new MenuItem(new JMenuItem("Русский"), "Русский");
        addMenuItem(langMenu, russian, event -> Localizer.changeLanguage(new Locale("ru", "RU")));

        MenuItem english = new MenuItem(new JMenuItem("English"), "English");
        addMenuItem(langMenu, english, event -> Localizer.changeLanguage(new Locale("en", "US")));

        MenuItem german = new MenuItem(new JMenuItem("Deutsch"), "Deutsch");
        addMenuItem(langMenu, german, event -> Localizer.changeLanguage(new Locale("de", "DE")));

        MenuItem chinese = new MenuItem(new JMenuItem("中文"), "中文");
        addMenuItem(langMenu, chinese, event -> Localizer.changeLanguage(new Locale("zh", "CN")));

        Localizer.registerListener(langMenu);
        return langMenu;
    }

    private MenuItem generateTestMenu(){
        MenuItem testMenu = new MenuItem(new JMenu(bundle.getString("testCommands")), "testCommands");
        testMenu.getItem().setMnemonic(KeyEvent.VK_T);
        testMenu.getItem().getAccessibleContext().
                setAccessibleDescription(bundle.getString("testCommands"));

        addMenuItem(testMenu, "newLine",
                event -> Logger.debug(bundle.getString("logMessage")));

        Localizer.registerListener(testMenu);

        return testMenu;
    }

    private MenuItem generateLookAndFeelMenu(){
        MenuItem lookAndFeelMenu =
                new MenuItem(new JMenu(bundle.getString("mode")), "mode");

        lookAndFeelMenu.getItem().setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getItem().getAccessibleContext()
                .setAccessibleDescription("Управление режимом отображения приложения");

        addMenuItem(lookAndFeelMenu, "sysColor", event -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });

        addMenuItem(lookAndFeelMenu, "univColor", event -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });

        Localizer.registerListener(lookAndFeelMenu);

        return lookAndFeelMenu;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}