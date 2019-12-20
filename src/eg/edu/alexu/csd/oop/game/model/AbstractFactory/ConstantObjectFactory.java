package eg.edu.alexu.csd.oop.game.model.AbstractFactory;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.objects.ConstantObject;

public class ConstantObjectFactory implements GameObjectFactory {
    @Override
    public GameObject getShape(int x, int y, String pathOrColor) {
        return new ConstantObject(x, y, pathOrColor);
    }

}
