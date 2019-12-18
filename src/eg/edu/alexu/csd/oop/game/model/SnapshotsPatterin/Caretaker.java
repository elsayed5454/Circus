package eg.edu.alexu.csd.oop.game.model.SnapshotsPatterin;

import java.util.ArrayList;

public class Caretaker {
    ArrayList<Memento> SavedCircus=new ArrayList<Memento>();

    public void addMemento(Memento m){
        SavedCircus.add(m);
    }
    public Memento getMemento(int index){
        return SavedCircus.get(index);
    }
}
