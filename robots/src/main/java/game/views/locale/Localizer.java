package game.views.locale;

import game.Main;
import game.MainApplicationFrame;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class Localizer {
    private static final ArrayList<LocaleChangeListener> listeners = new ArrayList<>();

    public static void changeLanguage(Locale locale) {
        MainApplicationFrame.bundle = ResourceBundle.getBundle("GameBundle", locale);
        ResourceBundle bundle = MainApplicationFrame.bundle;
        update(bundle);
    }

    private static void update(ResourceBundle bundle){
        for (LocaleChangeListener listener : listeners){
            listener.onLocaleChanged(bundle);
        }
    }

    public static void registerListener(LocaleChangeListener listener){
        listeners.add(listener);
    }
}