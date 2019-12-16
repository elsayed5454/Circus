package eg.edu.alexu.csd.oop.game.Iterator;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.LinkedList;
import java.util.List;

public class Iterator implements  IIterator {
    private  List<GameObject> List = new LinkedList<>();
    private int position;

    public Iterator(List<GameObject> List){
        this.List=List;
        position=0;
    }


    @Override
    public void first() {
        position =0;
    }

    @Override
    public GameObject next() {
        return List.get(position++);
    }

    @Override
    public GameObject currentItem() {
        return List.get(position);
    }

    @Override
    public GameObject get(int index) {
        return List.get(index);
    }

    @Override
    public boolean hasNext() {
        if(position >= List.size())
            return false;
        return true;
    }

    @Override
    public int size() {
        return (position+1);
    }
}
