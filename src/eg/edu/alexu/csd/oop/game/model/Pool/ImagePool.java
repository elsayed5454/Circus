package eg.edu.alexu.csd.oop.game.model.Pool;

import eg.edu.alexu.csd.oop.game.model.Flyweight.FlyweightImageFactory;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.objects.ImageObject;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

// Object Pool Pattern to reuse created objects when time objects expire
// or they reaches the height of the screen
public class ImagePool {
    private LinkedList<GameObject> available, inUse;
    private int sizeOnScreen;
    private int width, height;
    private Random rand = new Random();

    public ImagePool(int width, int height, int size) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.sizeOnScreen = size;
        this.width = width;
        this.height = height;
        initialize();
    }

    private void initialize() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        this.available = new LinkedList<>();
        this.inUse = new LinkedList<>();
        for(int i = 0; i < 100; i++) {
            available.add(createGameObject());
        }
        for(int i = 0; i < sizeOnScreen; i++) {
            inUse.add(createGameObject());
        }
    }

    private GameObject createGameObject() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        GameObject po = FlyweightImageFactory.getShape(0, -1 * rand.nextInt(height), randomColor(rand.nextInt(3)));
        po.setX(rand.nextInt(width - po.getWidth()));
        return po;
    }

    public void getGameObject() {

        final int Moving = 0;       // Moving state
        int movingPlatesNum = 0;    // Number of moving plates in game
        for(GameObject plate : inUse) {
            if(((ImageObject)plate).getState() == Moving) {
                movingPlatesNum++;
            }
        }

        // Add required number of plates on screen
        while (!available.isEmpty() && movingPlatesNum < sizeOnScreen) {
            GameObject plate = available.getFirst();
            available.remove(available.getFirst());
            inUse.add(plate);
            movingPlatesNum++;
        }
    }

    public void releaseToPool(GameObject po) {

        // Reset released gameObject
        po.setX(rand.nextInt(width - po.getWidth()));
        po.setY(-1 * rand.nextInt(height));
        available.add(po);
        inUse.remove(po);
    }

    public List<GameObject> getInUse() {
        getGameObject();
        return inUse;
    }

    // Random the color of shape
    private String randomColor(int color) {
        switch (color) {
            case 0:
                return "red";
            case 1:
                return "blue";
            default:
                return "green";
        }
    }

}
