package eg.edu.alexu.csd.oop.game.model.State;

public class Caught implements IState {

    //the plate has been caught by the clown
    @Override
    public int setState() {
        return 1;
    }
}
