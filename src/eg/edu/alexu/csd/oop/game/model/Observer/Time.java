package eg.edu.alexu.csd.oop.game.model.Observer;

import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;
import eg.edu.alexu.csd.oop.game.model.world.Circus;

import java.util.logging.Level;

public class Time implements  IObserver {

    public Time(Circus game) {
        game.register(this);
    }
    @Override
    public void update(Object updatedValue) {
        //System.out.println("time updated: "+ updatedValue);
        GameLogger.getInstance().log.warn("Hurry!! time left is"+ (int)updatedValue);
    }
}
