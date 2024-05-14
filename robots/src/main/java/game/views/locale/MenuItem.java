package game.views.locale;

import javax.swing.*;
import java.util.ResourceBundle;

public class MenuItem implements LocaleChangeListener {
    private final JMenuItem item;
    private final String label;
    public MenuItem(JMenuItem item, String label){
        this.item = item;
        this.label = label;
    }
    @Override
    public void onLocaleChanged(ResourceBundle bundle) {
        item.setText(bundle.getString(label));
    }
    public JMenuItem getItem(){ return this.item; }
    public void add(MenuItem item){
        this.item.add(item.getItem());
    }
}