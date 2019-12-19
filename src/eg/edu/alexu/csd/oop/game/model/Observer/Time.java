package eg.edu.alexu.csd.oop.game.model.Observer;

import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;
import eg.edu.alexu.csd.oop.game.world.Circus;

public class Time implements  IObserver {

    private Circus game;

    public Time(Circus game) {
        this.game = game;
        game.register(this);
    }
    @Override
    public void update(int updatedValue) {
        //System.out.println("time updated: "+ updatedValue);
        GameLogger.getInstance().logger.info("time updated: "+ updatedValue);
    }
}
