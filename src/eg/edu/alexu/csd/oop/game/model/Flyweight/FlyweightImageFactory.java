package eg.edu.alexu.csd.oop.game.model.Flyweight;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.objects.ImageObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Flyweight pattern to create objects faster by storing them in
// a hash map and get them faster
public class FlyweightImageFactory {
    static Map<String, BufferedImage[]> platesFactory = new HashMap<String, BufferedImage[]>();
    private static final int MOVING = 0;

    public FlyweightImageFactory() {
        try {
            platesFactory.put("/clown.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/clown.png"))});
            platesFactory.put("/rightStick.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/rightStick.png"))});
            platesFactory.put("/leftStick.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/leftStick.png"))});
            platesFactory.put("/reddd.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/reddd.png"))});
            platesFactory.put("/blueee.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/blueee.png"))});
            platesFactory.put("/greennn.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/greennn.png"))});
            platesFactory.put("/circus.jpg", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/circus.jpg"))});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameObject getImageObject(int x, int y, String path) {
        return new ImageObject(x, y, MOVING, path.equals("/rightStick.png") || path.equals("/leftStick.png"), platesFactory.get(path));
    }
}
