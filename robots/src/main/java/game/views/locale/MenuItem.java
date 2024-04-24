package game.views.locale;

import javax.swing.*;
import java.util.ResourceBundle;

public class MenuItem implements LocaleChangeListener {
    private final JMenuItem item;
    public JMenuItem getItem(){ return this.item; }
    private final String label;
    @Override
    public void onLocaleChanged(ResourceBundle bundle) {
        item.setText(bundle.getString(label));
    }
    private MenuItem(JMenuItem item, String label){
        this.item = item;
        this.label = label;
    }
    public static MenuItem instantiate(JMenuItem item, String label){
        return new MenuItem(item, label);
    }
    public void add(MenuItem item){
        this.item.add(item.getItem());
    }
}