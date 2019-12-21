package eg.edu.alexu.csd.oop.game.model.Snapshot;

import java.util.ArrayList;

public class Caretaker {
    ArrayList<Memento> SavedCircus;
    private static Caretaker instance;

    public static Caretaker getInstance() {
        if (Caretaker.instance == null) {
            Caretaker.instance = new Caretaker();
        }
        return Caretaker.instance;
    }

    private Caretaker() {
        SavedCircus = new ArrayList<>();
    }

    public void addMemento(Memento m) {
        SavedCircus.add(m);
        System.out.println(SavedCircus.size());
    }

    public Memento getMemento(int index) {
        return SavedCircus.get(index);
    }

}
