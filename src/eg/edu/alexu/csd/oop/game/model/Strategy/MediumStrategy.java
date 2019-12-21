package eg.edu.alexu.csd.oop.game.model.Strategy;

import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;

public class MediumStrategy implements IStrategy {
    GameLogger gameLogger = GameLogger.getInstance();
    @Override
    public int speed() {
        gameLogger.log.debug(" The Speed Now is 8");
        return 8;
    }

    @Override
    public int controlSpeed() {
        gameLogger.log.debug(" The Speed Now is 10");
        return 10;
    }

    @Override
    public int differenceBetweenPlateAndStick() {
        gameLogger.log.debug(" The Difference Between Plate And Stick is 30 ");
        return 30;
    }

    @Override
    public int differenceBetweenPlateAndPlate() {
        gameLogger.log.debug(" The Difference Between Plate And Plate is 40 ");
        return 40;
    }
}
