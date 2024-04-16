package game.viewmodels;

import game.model.Entity;
import game.model.GameModel;

import java.util.List;

public class SimpleEntityProvider implements EntitiesProvider {
    private final GameModel gameModel;

    public SimpleEntityProvider(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public List<Entity> getEntities() {
        return gameModel.getEntities();
    }
}
