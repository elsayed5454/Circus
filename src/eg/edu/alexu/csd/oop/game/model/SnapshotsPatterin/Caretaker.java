package eg.edu.alexu.csd.oop.game.model.SnapshotsPatterin;

import java.util.ArrayList;

public class Caretaker {
    ArrayList<Memento> SavedCircus=new ArrayList<Memento>();

    public void addMemento(Memento m){
        SavedCircus.add(m);
        System.out.println(SavedCircus.size());
    }
    public Memento getMemento(int index){
        return SavedCircus.get(index);
    }
    public void RemoveAfterIndex(int index){
        for(int i = index-1 ; i< SavedCircus.size() ; i++){
            SavedCircus.remove(i);
        }
    }
    public int getSize(){
        return SavedCircus.size();
    }
}
