package eg.edu.alexu.csd.oop.game.model.State;

import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;

public class Moving implements IState {
    private GameLogger gameLogger = GameLogger.getInstance();
    //the plate is moving downwards
    public int setState() {
        gameLogger.log.debug(" The Plate is Moving ");
        return 0;
    }
}
