package game.model;

import java.util.ArrayList;
import java.util.List;

public class ModelContext {
    private final GameModel model;
    public ModelContext(GameModel model) {
        this.model = model;
    }

    public <T extends Entity> List<T> findEntities(Class<T> availableEntitiesType) {
        List<T> found = new ArrayList<>();
        for (Entity entity : model.getEntities()) {
            if (availableEntitiesType.isInstance(entity)) {
                found.add(availableEntitiesType.cast(entity));
            }
        }
        return found;
    }
}
