package game.views.locale;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;


public class Localizer {
    private static final CopyOnWriteArrayList<LocaleChangeListener> listeners = new CopyOnWriteArrayList<>();
    public static ResourceBundle bundle =
            ResourceBundle.getBundle("GameBundle", new Locale("en", "US"));

    public static void changeLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("GameBundle", locale);
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