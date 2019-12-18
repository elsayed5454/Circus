package eg.edu.alexu.csd.oop.game.model.Iterator;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.List;

public class GameObjectIterator implements IIterator {
    private List<GameObject> List;
    private int position;

    public GameObjectIterator(List<GameObject> List) {
        this.List = List;
        position = 0;
    }

    @Override
    public void first() {
        position = 0;
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
        return position < List.size();
    }

    @Override
    public int size() {
        return (position + 1);
    }
}
