package eg.edu.alexu.csd.oop.game.model;

import eg.edu.alexu.csd.oop.game.objects.ImageObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class ShapesFactory {

    private static final HashMap<String, ImageObject> imageObjectsBYPath = new HashMap<>();
    private static final int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static Random rand = new Random();

    public static ImageObject getShape(String path) {
        ImageObject imageObject = imageObjectsBYPath.get(path);

        if (imageObject == null) {

            String newPath = randomPlate(rand.nextInt(3));
            imageObject = new ImageObject(0, -1 * rand.nextInt(height), newPath);
            imageObject.setX(rand.nextInt(width - imageObject.getWidth()));
            imageObjectsBYPath.put(newPath, imageObject);
        }
        return imageObject;
    }

    // Deciding the color
    private static String randomPlate(int color) {
        switch (color) {
            case 0:
                return "/reddd.png";
            case 1:
                return "/blueee.png";
            case 2:
                return "/greennn.png";
        }
        return null;
    }
}
