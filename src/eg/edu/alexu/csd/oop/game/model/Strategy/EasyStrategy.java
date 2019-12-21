package eg.edu.alexu.csd.oop.game.model.Strategy;

import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;

public class EasyStrategy implements IStrategy {
    GameLogger gameLogger = GameLogger.getInstance();
    @Override
    public int speed() {
        gameLogger.log.debug(" the Speed Now is 10 ");
        return 10;
    }

    @Override
    public int controlSpeed() {
        gameLogger.log.debug(" the Control Speed Now is 15 ");
        return 15;
    }

    @Override
    public int differenceBetweenPlateAndStick() {
        gameLogger.log.debug(" The Difference Between Plate And Stick is 40 ");
        return 40;
    }

    @Override
    public int differenceBetweenPlateAndPlate() {
        gameLogger.log.debug(" The Difference Between Plate And Plate is 60 ");
        return 60;
    }
}
