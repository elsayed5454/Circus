package eg.edu.alexu.csd.oop.game.model.AbstractFactory;

import eg.edu.alexu.csd.oop.game.GameObject;

public abstract class GameObjectBuilding {

    protected abstract GameObject makeGameObject(String gameObjectType);
    
}
