package eg.edu.alexu.csd.oop.game.model.Shapes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pot implements IShape {
    private BufferedImage[] spriteImages;
    private String color;

    public Pot(String color) throws IOException {
        this.color = color;
        spriteImages = new BufferedImage[]{ImageIO.read(getClass().getResourceAsStream("/" + color + "pot.png"))};
    }

    @Override
    public BufferedImage[] getImage() {
        return spriteImages;
    }

    @Override
    public String getColor() {
        return color;
    }
}
