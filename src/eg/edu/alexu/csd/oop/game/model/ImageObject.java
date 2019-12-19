package eg.edu.alexu.csd.oop.game.model;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.Shapes.IShape;

import java.awt.image.BufferedImage;

public class ImageObject implements GameObject {

    // An array of sprite images that are drawn sequentially
    private BufferedImage[] spriteImages;
    private int x, y;
    private boolean visible;
    private int type;
    private int distFromStick;
    private boolean horizontalOnly;

    public ImageObject(int posX, int posY, int type, IShape shape) {
        this(posX, posY, type, false, shape.getImage());
    }

    public ImageObject(int posX, int posY, int type, boolean horizontalOnly, BufferedImage[] spriteImage) {
        this.x = posX;
        this.y = posY;
        this.type = type;
        this.visible = true;
        this.horizontalOnly = horizontalOnly;
        // Create a bunch of buffered images and place into an array, to be displayed sequentially
        this.spriteImages = spriteImage;
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
        if(horizontalOnly) {
            return;
        }
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDistFromStick() {
        return distFromStick;
    }

    public void setDistFromStick(int distFromStick) {
        this.distFromStick = distFromStick;
    }

    public void setHorizontalOnly() {
        this.horizontalOnly = true;
    }

}

