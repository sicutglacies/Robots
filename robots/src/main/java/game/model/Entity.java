package game.model;

import java.beans.PropertyChangeListener;

public interface Entity extends PropertyChangeListener {
    void update();
}