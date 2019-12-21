package eg.edu.alexu.csd.oop.game.model.Strategy;

import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;

public class HardStrategy implements IStrategy{
    GameLogger gameLogger = GameLogger.getInstance();
    @Override
    public int speed() {
        gameLogger.log.debug(" The Speed Now is 6 ");
        return 6;
    }

    @Override
    public int controlSpeed() {
        gameLogger.log.debug(" The Speed Now is 7");
        return 7;
    }

    @Override
    public int differenceBetweenPlateAndStick()
    {
        gameLogger.log.debug(" The Difference Between Plate And Stick is 20 ");
        return 20;
    }

    @Override
    public int differenceBetweenPlateAndPlate() {
        gameLogger.log.debug(" The Difference Between Plate And Plate is 25 ");
        return 25;
    }
}
