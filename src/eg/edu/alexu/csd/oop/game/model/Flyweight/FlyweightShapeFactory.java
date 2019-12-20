package eg.edu.alexu.csd.oop.game.model.Flyweight;

import eg.edu.alexu.csd.oop.game.model.DynamicLinkage.DynamicLinkage;
import eg.edu.alexu.csd.oop.game.model.Shapes.IShape;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

// Flyweight pattern to create objects faster by storing them in
// a hash map and get them faster
public class FlyweightShapeFactory {

    private LinkedList<Class<?>> shapesClasses = new LinkedList<>();
    private Map<String, IShape> shapesMap = new HashMap<>();
    private static FlyweightShapeFactory instance;

    public static FlyweightShapeFactory getInstance(List<String> jars) {
        if (FlyweightShapeFactory.instance == null) {
            FlyweightShapeFactory.instance = new FlyweightShapeFactory(jars);
        }

        return FlyweightShapeFactory.instance;
    }

    private FlyweightShapeFactory(List<String> jars) {
        try {
            DynamicLinkage shapesLoader = new DynamicLinkage();
            shapesClasses = shapesLoader.loadShapesClasses(jars);

            // Store all shapes with all colors
            IShape tmpShape = null;
            String[] colors = {"red", "green", "blue", "yellow", "black", "cyan", "orange", "pink", "purple"};
            for(String color : colors) {
                for(Class<?> clazz : shapesClasses) {
                    try {
                        tmpShape = (IShape) clazz.getConstructor(String.class).newInstance(color);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    // Ex: key: "redPot" & value: its shape
                    shapesMap.put(color + clazz.getSimpleName(), tmpShape);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public IShape getShape(String color) {
        int rand = new Random().nextInt(shapesClasses.size());
        return shapesMap.get(color + shapesClasses.get(rand).getSimpleName());
    }
}
