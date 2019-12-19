package eg.edu.alexu.csd.oop.game.model.SnapshotsPatterin;

import eg.edu.alexu.csd.oop.game.model.world.Circus;

public class Originator {

    private Circus state ;

    // Sets the value for the article

    public void set(Circus neww) {
        this.state = neww;
    }

    // Creates a new Memento with a new article

    public Memento storeInMemento() {
        return new Memento( this.state);
    }

    // Gets the article currently stored in memento

    public Circus restoreFromMemento(Memento memento) {

        state =  memento.getSaved();
        return  this.state;

    }

}
