package eg.edu.alexu.csd.oop.game.objects;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import eg.edu.alexu.csd.oop.game.GameObject;

public class ImageObject implements GameObject{
    private static final int MAX_MSTATE = 1;
    // an array of sprite images that are drawn sequentially
    private BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
    private int x;
    private int y;
    private boolean visible;
    private int type;

    public ImageObject(int posX, int posY, String path){
        this(posX, posY, path, 0);
    }

    public ImageObject(int posX, int posY, String path, int type){
        this.x = posX;
        this.y = posY;
        this.type = type;
        this.visible = true;
        // create a bunch of buffered images and place into an array, to be displayed sequentially
        try {
            spriteImages[0] = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * setter/getter for X position
     */
    @Override
    public int getX() {
        return x;
    }
    /**
     * setter/getter for X position
     */
    @Override
    public void setX(int mX) {
        this.x = mX;
    }
    /**
     * setter/getter for Y position
     */
    @Override
    public int getY() {
        return y;
    }
    /**
     * setter/getter for Y position
     */
    @Override
    public void setY(int mY) {
        this.y = mY;
    }
    /**
     * @return object movement frames
     */
    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }
    /**
     * @return object width
     */
    @Override
    public int getWidth(){
        return spriteImages[0].getWidth();
    }
    /**
     * @return object height
     */
    @Override
    public int getHeight() {
        return spriteImages[0].getHeight();
    }
    /**
     * @return object visible or not
     */
    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}

