package eg.edu.alexu.csd.oop.game.model.AbstractFactory;

import eg.edu.alexu.csd.oop.game.GameObject;

public interface GameObjectFactory {

    GameObject getShape(int x, int y, String pathOrColor);
}
