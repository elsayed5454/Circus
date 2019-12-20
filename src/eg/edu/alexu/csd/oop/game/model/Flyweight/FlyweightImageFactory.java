package eg.edu.alexu.csd.oop.game.model.Flyweight;

import eg.edu.alexu.csd.oop.game.model.DynamicLinkage.DynamicLinkage;
import eg.edu.alexu.csd.oop.game.model.State.Moving;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.Shapes.IShape;
import eg.edu.alexu.csd.oop.game.model.objects.ImageObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

// Flyweight pattern to create objects faster by storing them in
// a hash map and get them faster
public class FlyweightImageFactory {

    private Map<String, BufferedImage[]> platesFactory = new HashMap<>();
    private static LinkedList<Class<?>> shapesClasses = new LinkedList<>();

    public FlyweightImageFactory(List<String> jars) {
        try {
            platesFactory.put("/clown.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/clown.png"))});
            platesFactory.put("/rightStick.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/rightStick.png"))});
            platesFactory.put("/leftStick.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/leftStick.png"))});
            platesFactory.put("/circus.jpg", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/circus.jpg"))});

            DynamicLinkage shapesLoader = new DynamicLinkage();
            shapesClasses = shapesLoader.loadShapesClasses(jars);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public GameObject getImageObject(int x, int y, String path) {
        return new ImageObject(x, y, new Moving(), path.equals("/rightStick.png") || path.equals("/leftStick.png"), platesFactory.get(path));
    }

    public static GameObject getShape(int x, int y, String color) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int rand = new Random().nextInt(shapesClasses.size());
        IShape shape = (IShape) shapesClasses.get(rand).getConstructor(String.class).newInstance(color);
        return new ImageObject(x, y, new Moving(), shape);
    }
}
