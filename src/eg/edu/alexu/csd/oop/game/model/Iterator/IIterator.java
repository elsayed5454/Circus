package eg.edu.alexu.csd.oop.game.model.Iterator;

import eg.edu.alexu.csd.oop.game.GameObject;

public interface IIterator {

    void next();//To get the next element

    GameObject currentItem();//To retrieve the current element

    boolean hasNext();//To check whether there is any next element or not.
}
