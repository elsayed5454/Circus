package eg.edu.alexu.csd.oop.game.model.Pool;

import eg.edu.alexu.csd.oop.game.model.Flyweight.FlyweightImageFactory;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// Object Pool Pattern to reuse created objects when time objects expire
// or they reaches the height of the screen
public class ImagePool {
    public HashMap<GameObject, Long> available = new HashMap<>();
    public HashMap<GameObject, Long> inUse = new HashMap<>();
    private int width, height;
    private Random rand = new Random();
    private FlyweightImageFactory flyweightimageFactory;

    public ImagePool(int width, int height, List<String> jars) {
        this.width = width;
        this.height = height;
        flyweightimageFactory = new FlyweightImageFactory(jars);
    }

    public GameObject getGameObject() {
        long now = System.currentTimeMillis();
        if (!available.isEmpty()) {
            for (Map.Entry<GameObject, Long> entry : available.entrySet()) {
                long expTime = 6000;
                if (now - entry.getValue() > expTime) {
                    popElement(available);
                } else {
                    GameObject po = popElement(available, entry.getKey());
                    push(inUse, po, now);
                    return po;
                }
            }
        }
        return createGameObject(now);
    }

    private GameObject createGameObject(long now) {
        GameObject po = flyweightimageFactory.getImageObject(0, -1 * rand.nextInt(height), randomPlate(rand.nextInt(3)));
        po.setX(rand.nextInt(width - po.getWidth()));
        push(inUse, po, now);
        return po;
    }

    private void push(HashMap<GameObject, Long> map, GameObject po, long now) {
        map.put(po, now);
    }

    public void releaseObject(GameObject po) {
        available.put(po, System.currentTimeMillis());
        inUse.remove(po);
    }

    private GameObject popElement(HashMap<GameObject, Long> map) {
        Map.Entry<GameObject, Long> entry = map.entrySet().iterator().next();
        GameObject key = entry.getKey();
        map.remove(entry.getKey());
        return key;
    }

    private GameObject popElement(HashMap<GameObject, Long> map, GameObject key) {
        map.remove(key);
        return key;
    }

    // Deciding the color
    private String randomPlate(int color) {
        switch (color) {
            case 0:
                return "/reddd.png";
            case 1:
                return "/blueee.png";
            default:
                return "/greennn.png";
        }
    }
}
