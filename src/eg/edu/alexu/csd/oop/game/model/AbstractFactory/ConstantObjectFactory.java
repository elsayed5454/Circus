package eg.edu.alexu.csd.oop.game.model.AbstractFactory;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;
import eg.edu.alexu.csd.oop.game.model.objects.ConstantObject;

public class ConstantObjectFactory implements GameObjectFactory {
    private GameLogger gameLogger =GameLogger.getInstance();
    @Override
    public GameObject getShape(int x, int y, String pathOrColor)
    {
        gameLogger.log.debug(" The Background is Successfully Created ");
        return new ConstantObject(x, y, pathOrColor);
    }

}
