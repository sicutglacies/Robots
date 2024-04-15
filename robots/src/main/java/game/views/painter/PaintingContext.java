package game.views.painter;

import game.model.Robot;
import game.model.Target;

import java.awt.*;
import java.util.Map;

public class PaintingContext {
    private final Map<Class<?>, Painter<?>> painters;

    public PaintingContext() {
        this.painters = Map.of(
            Robot.class, new RobotPainter(),
            Target.class, new TargetPainter()
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
