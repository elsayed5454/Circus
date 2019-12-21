package eg.edu.alexu.csd.oop.game.model.State;

import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;

public class Falling implements IState {
    private GameLogger gameLogger = GameLogger.getInstance();
    //the plate is falling from the clown
    public int setState() {
        gameLogger.log.debug(" The Plate is Falling ");
        return 2;
    }
}
