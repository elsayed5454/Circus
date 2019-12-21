package eg.edu.alexu.csd.oop.game.model.AbstractFactory;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;
import eg.edu.alexu.csd.oop.game.model.objects.ControllableObject;

public class ControllableObjectFactory implements GameObjectFactory {
    private GameLogger gameLogger = GameLogger.getInstance();
    @Override
    public GameObject getShape(int x, int y, String pathOrColor)
    {
        gameLogger.log.debug(" The Clown is Successfully Created ");
        return new ControllableObject(x, y, pathOrColor);
    }

}
