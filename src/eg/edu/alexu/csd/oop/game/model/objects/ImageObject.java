package eg.edu.alexu.csd.oop.game.model.objects;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.Shapes.IShape;
import eg.edu.alexu.csd.oop.game.model.State.IState;

import java.awt.image.BufferedImage;

public class ImageObject implements GameObject {

    // An array of sprite images that are drawn sequentially
    private BufferedImage[] spriteImages;
    private int x, y;
    private boolean visible;
    private int state;
    private int distFromStick;
    private boolean horizontalOnly;
    private String color = "white";

    public ImageObject(int posX, int posY, IState state, IShape shape) {
        this(posX, posY, state, false, shape.getImage());
        this.color = shape.getColor();
    }

    public ImageObject(int posX, int posY, IState state, boolean horizontalOnly, BufferedImage[] spriteImage) {
        this.x = posX;
        this.y = posY;
        this.state = state.setState();
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
        if (horizontalOnly) {
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

    public int getState() {
        return state;
    }

    public void setState(IState state) {
        this.state = state.setState();
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
    public String getColor() {
        return color;
    }


}

