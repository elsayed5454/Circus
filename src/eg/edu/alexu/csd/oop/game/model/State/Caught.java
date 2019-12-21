package eg.edu.alexu.csd.oop.game.model.State;

import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;

public class Caught implements IState {
    private GameLogger gameLogger = GameLogger.getInstance();
    //the plate has been caught by the clown
    @Override
    public int setState() {
        gameLogger.log.debug(" The Plate is caught ");
        return 1;
    }
}
