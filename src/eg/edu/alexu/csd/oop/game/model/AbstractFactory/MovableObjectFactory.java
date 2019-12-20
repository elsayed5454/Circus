package eg.edu.alexu.csd.oop.game.model.AbstractFactory;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.Flyweight.FlyweightShapeFactory;
import eg.edu.alexu.csd.oop.game.model.Shapes.IShape;
import eg.edu.alexu.csd.oop.game.model.State.IState;
import eg.edu.alexu.csd.oop.game.model.State.Moving;
import eg.edu.alexu.csd.oop.game.model.objects.MovableObject;

import java.util.List;

public class MovableObjectFactory implements GameObjectFactory {
    private final IState movingState = new Moving();
    FlyweightShapeFactory shapeFactory;
    private static MovableObjectFactory instance;

    public static MovableObjectFactory getInstance(List<String> jars) {
        if (MovableObjectFactory.instance == null) {
            MovableObjectFactory.instance = new MovableObjectFactory(jars);
        }

        return MovableObjectFactory.instance;
    }

    // Must be initialized with jars first
    public static MovableObjectFactory getInstance() {
        return MovableObjectFactory.instance;
    }

    private MovableObjectFactory(List<String> jars) {
        shapeFactory = FlyweightShapeFactory.getInstance(jars);
    }

    @Override
    public GameObject getShape(int x, int y, String pathOrColor) {
        IShape shape = shapeFactory.getShape(pathOrColor);
        return new MovableObject(x, y, movingState, pathOrColor, shape.getImage());
    }

}
