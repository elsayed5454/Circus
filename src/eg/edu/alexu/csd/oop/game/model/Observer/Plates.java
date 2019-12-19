package eg.edu.alexu.csd.oop.game.model.Observer;

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
               System.out.println("Intersection of two plates");
               //GameLogger.getInstance().log.info("Intersection of two plates" );
           }
           else if (updatedValue==2){
               System.out.println("3 plates of same color ");
               //GameLogger.getInstance().log.info("3 plates of same color " );
           }
    }
}
