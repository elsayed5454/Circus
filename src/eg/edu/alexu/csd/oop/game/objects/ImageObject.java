package eg.edu.alexu.csd.oop.game.objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import eg.edu.alexu.csd.oop.game.GameObject;

public class ImageObject implements GameObject {
    private static final int MAX_STATE = 1;
    private static final int MOVING = 0;
    // An array of sprite images that are drawn sequentially
    private BufferedImage[] spriteImages = new BufferedImage[MAX_STATE];
    private int x, y;
    private boolean visible;
    private int type;
    private String path;

    public ImageObject(int posX, int posY, String path) {
        this(posX, posY, path, MOVING);
    }

    public ImageObject(int posX, int posY, String path, int type) {
        this.x = posX;
        this.y = posY;
        this.path = path;
        this.type = type;
        this.visible = true;
        // Create a bunch of buffered images and place into an array, to be displayed sequentially
        try {
            spriteImages[0] = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int mX) {
        this.x = mX;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int mY) {
        this.y = mY;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }

    @Override
    public int getWidth() {
        return spriteImages[0].getWidth();
    }

    @Override
    public int getHeight() {
        return spriteImages[0].getHeight();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return this.path;
    }

}

