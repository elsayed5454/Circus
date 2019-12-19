package eg.edu.alexu.csd.oop.game.model.Flyweight;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.DynamicLinkage;
import eg.edu.alexu.csd.oop.game.model.ImageObject;
import eg.edu.alexu.csd.oop.game.model.Shapes.IShape;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

// Flyweight pattern to create objects faster by storing them in
// a hash map and get them faster
public class FlyweightImageFactory {
    private static Map<String, BufferedImage[]> platesFactory = new HashMap<>();
    private static final int MOVING = 0;
    private DynamicLinkage shapesLoader = new DynamicLinkage();
    private LinkedList<Class<?>> shapesClasses = new LinkedList<>();

    public FlyweightImageFactory() {
        try {
            platesFactory.put("/clown.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/clown.png"))});
            platesFactory.put("/rightStick.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/rightStick.png"))});
            platesFactory.put("/leftStick.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/leftStick.png"))});
            platesFactory.put("/circus.jpg", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/circus.jpg"))});

            shapesClasses = shapesLoader.loadShapesClasses();
            //platesFactory.put("/reddd.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/reddd.png"))});
            //platesFactory.put("/blueee.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/blueee.png"))});
            //platesFactory.put("/greennn.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/greennn.png"))});

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public GameObject getImageObject(int x, int y, String path) {
        return new ImageObject(x, y, MOVING, path.equals("/rightStick.png") || path.equals("/leftStick.png"), platesFactory.get(path));
    }

    public GameObject getShape(int x, int y, String color) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int rand = new Random().nextInt(shapesClasses.size());
        IShape shape = (IShape) shapesClasses.get(rand).getConstructor(String.class).newInstance(color);
        return new ImageObject(x, y, MOVING, shape);
    }
}
