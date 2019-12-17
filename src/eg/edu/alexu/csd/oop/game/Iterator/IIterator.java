package eg.edu.alexu.csd.oop.game.Iterator;

import eg.edu.alexu.csd.oop.game.GameObject;

public interface IIterator {
    void first();//Reset to first element
    GameObject next();//To get the next element
    GameObject currentItem();//To retrieve the current element
    GameObject get(int index);
    boolean hasNext();//To check whether there is any next element or not.
    int size();
}
