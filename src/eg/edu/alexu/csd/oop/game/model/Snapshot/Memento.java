package eg.edu.alexu.csd.oop.game.model.Snapshot;

import eg.edu.alexu.csd.oop.game.model.world.Circus;

public class Memento {

    // The states stored in memento Object
    private Circus state;

    // Save a new objects list to the memento Object
    public Memento(Circus C) {
        this.state = C;
    }

    // Return the value stored
    public Circus getSaved() {
        return this.state;
    }
}
