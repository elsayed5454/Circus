package eg.edu.alexu.csd.oop.game.model.Observer;

import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;
import eg.edu.alexu.csd.oop.game.model.world.Circus;

public class Plates implements IObserver {


    Circus game;

    public Plates(Circus game) {
        this.game = game;
        game.register(this);
    }
    @Override
    public void update(Object updatedValue) {

           if (updatedValue instanceof Boolean){
               //System.out.println("3 plates of same color ");
               GameLogger.getInstance().logger.info("3 plates of same color " );
           }
        else  {
            //System.out.println("Intersection is happened");
            GameLogger.getInstance().logger.info("Intersection is happened" );
        }
    }
}
