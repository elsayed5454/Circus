package eg.edu.alexu.csd.oop.game.model.Iterator;

import eg.edu.alexu.csd.oop.game.GameObject;


public class GameObjectList implements IList {
    private java.util.List<GameObject> List;

    public GameObjectList(java.util.List<GameObject> List) {
        this.List = List;
    }

    @Override
    public IIterator createIterator() {
        return new GameObjectIterator(List);
    }
}
