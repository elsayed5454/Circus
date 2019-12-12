package eg.edu.alexu.csd.oop.game.objects;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.image.BufferedImage;

public class ClownObject implements GameObject {
    /**
     * setter/getter for X position
     */
    @Override
    public int getX() {
        return 0;
    }

    @Override
    public void setX(int x) {

    }

    /**
     * setter/getter for Y position
     */
    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setY(int y) {

    }

    /**
     * @return object width
     */
    @Override
    public int getWidth() {
        return 0;
    }

    /**
     * @return object height
     */
    @Override
    public int getHeight() {
        return 0;
    }

    /**
     * @return object visible or not
     */
    @Override
    public boolean isVisible() {
        return false;
    }

    /**
     * @return object movement frames
     */
    @Override
    public BufferedImage[] getSpriteImages() {
        return new BufferedImage[0];
    }
}
