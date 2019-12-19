package eg.edu.alexu.csd.oop.game.model.Observer;

import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;
import eg.edu.alexu.csd.oop.game.world.Circus;

public class Plates implements IObserver {


    Circus game;

    public Plates(Circus game) {
        this.game = game;
        game.register(this);
    }
    @Override
    public void update(int updatedValue) {
           if(updatedValue==1) {
               //System.out.println("Intersection is happened");
               GameLogger.getInstance().logger.info("Intersection is happened" );
           }
           else if (updatedValue==2){
               //System.out.println("3 plates of same color ");
               GameLogger.getInstance().logger.info("3 plates of same color " );
           }
    }
}
