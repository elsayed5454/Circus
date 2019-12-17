package eg.edu.alexu.csd.oop.game.Flyweight;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.objects.ImageObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FlyweightimageFactory  {
    static Map<String, BufferedImage[]> platesFactory = new HashMap<String, BufferedImage[]>();
    private static final int MOVING = 0;

    public FlyweightimageFactory() {
        try {
            platesFactory.put("/clown.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/clown.png"))});
            platesFactory.put("/rightStick.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/rightStick.png"))});
            platesFactory.put("/leftStick.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/leftStick.png"))});
            platesFactory.put("/reddd.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/reddd.png"))});
            platesFactory.put("/blueee.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/blueee.png"))});
            platesFactory.put("/greennn.png", new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/greennn.png"))});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  GameObject getimageFromFactory(int x , int y , String path){
        return new ImageObject(x, y, MOVING, (path.equals("/rightStick.png") || path.equals("/leftStick.png")

        ) ? true : false, platesFactory.get(path));
    }
}
