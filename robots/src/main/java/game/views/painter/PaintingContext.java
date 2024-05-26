package game.views.painter;

import game.model.*;

import java.awt.*;
import java.util.Map;

public class PaintingContext {
    private final Map<Class<?>, Painter<?>> painters;

    public PaintingContext() {
        this.painters = Map.of(
            Ally.class, new AllyPainter(),
            Enemy.class, new EnemyPainter(),
            Target.class, new TargetPainter(),
            Wall.class, new WallPainter()
        );
    }

    public void paint(Graphics2D graphics, Object paintedObject) {
        Class<?> objectClass = paintedObject.getClass();
        Painter<Object> painter = (Painter<Object>) painters.get(objectClass);
        if (painter == null) {
            throw new IllegalArgumentException("Has no painter for class: " + objectClass);
        }
        painter.paint(graphics, paintedObject, this);
    }
}
