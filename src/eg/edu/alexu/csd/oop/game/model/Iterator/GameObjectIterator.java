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
    public void next() {
        position ++;
    }

    @Override
    public GameObject currentItem() {
        return List.get(position);
    }

    @Override
    public boolean hasNext() {
        return position < List.size();
    }

}
