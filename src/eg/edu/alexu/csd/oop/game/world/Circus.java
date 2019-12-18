package eg.edu.alexu.csd.oop.game.world;

import eg.edu.alexu.csd.oop.game.model.Flyweight.FlyweightimageFactory;
import eg.edu.alexu.csd.oop.game.engineInterfaces.GameObject;
import eg.edu.alexu.csd.oop.game.model.Iterator.IIterator;
import eg.edu.alexu.csd.oop.game.model.Iterator.IList;
import eg.edu.alexu.csd.oop.game.model.Pool.ImagePool;
import eg.edu.alexu.csd.oop.game.engineInterfaces.World;
import eg.edu.alexu.csd.oop.game.model.objects.ImageObject;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Circus implements World {
    private final int MAX_TIME = 2 * 60 * 1000; // 2 minute
    // The system time when the game starts
    private final long startTime = System.currentTimeMillis();
    private final List<GameObject> constant = new LinkedList<>();       // Non moving objects in the game
    private final List<GameObject> movable = new LinkedList<>();        // Auto moving objects in the game
    private final List<GameObject> controllable = new LinkedList<>();   // Objects that the user control its movement in the game
    private final LinkedList<GameObject> rightStickPlates = new LinkedList<>();
    private final LinkedList<GameObject> leftStickPlates = new LinkedList<>();
    private final int width;    // The width of the screen
    private final int height;   // The height of the screen
    private int score = 0;      // Current score
    private static final int MOVING = 0;    // Plate is moving
    private static final int CATCHED = 1;   // Plate is catched
    private static final int FALLING = 2;   // Plate is falling
    private boolean isRightStickEmpty = true;
    private boolean isLeftStickEmpty = true;

    private final Random rand = new Random();

    private IList movableList;
    private IIterator movableIterator;
    private IList controllableList;
    private IIterator controllableIterator;
    FlyweightimageFactory FlyweightimageFactory = new FlyweightimageFactory();
    ImagePool imagePool;

    private final int speed; // Frequency
    private final int controlSpeed; // Control Frequency
    private final int differenceBetweenPlateAndStick;
    private final int differenceBetweenPlateAndPlate;

    public Circus(int width, int height, int gameLevel) {
        this.width = width;
        this.height = height;
        imagePool = new ImagePool(width, height);

        constant.add(FlyweightimageFactory.getimageFromFactory(0, 0, "/circus.jpg"));

        // The clown
        controllable.add(FlyweightimageFactory.getimageFromFactory(0, 0, "/clown.png"));
        controllable.get(0).setX(width / 2 - controllable.get(0).getWidth() / 2);
        controllable.get(0).setY((int) (height * 0.96 - controllable.get(0).getHeight()));
        ((ImageObject) controllable.get(0)).setHorizontalOnly();

        // The two sticks
        int clownHandHeight = controllable.get(0).getY() + (int) (controllable.get(0).getHeight() * 0.01);
        controllable.add(FlyweightimageFactory.getimageFromFactory(controllable.get(0).getX() + (int) (controllable.get(0).getWidth() * 0.7), clownHandHeight, "/rightStick.png"));
        controllable.add(FlyweightimageFactory.getimageFromFactory(controllable.get(0).getX() + (int) (controllable.get(0).getWidth() * 0.18), clownHandHeight, "/leftStick.png"));

        // Plates with random place to appear at and random color
        for (int i = 0; i < 14; i++) {
            movable.add(imagePool.getGameObject());
        }

        IList movableList = new eg.edu.alexu.csd.oop.game.model.Iterator.List(movable);
        IIterator movableIterator = movableList.createIterator();
        controllableList = new eg.edu.alexu.csd.oop.game.model.Iterator.List(controllable);
        controllableIterator = controllableList.createIterator();

        if (gameLevel == 0) {
            this.speed = 10;
            this.controlSpeed = 15;
            this.differenceBetweenPlateAndStick = 40;
            this.differenceBetweenPlateAndPlate = 60;
        } else if (gameLevel == 1) {
            this.speed = 8;
            this.controlSpeed = 10;
            this.differenceBetweenPlateAndStick = 30;
            this.differenceBetweenPlateAndPlate = 40;
        } else {
            this.speed = 6;
            this.controlSpeed = 7;
            this.differenceBetweenPlateAndStick = 20;
            this.differenceBetweenPlateAndPlate = 25;
        }
    }

    @Override
    public boolean refresh() {
        // Time ends and game over
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME;

        // Bound sticks to clown position
        GameObject clown = controllable.get(0);
        GameObject rightStick = controllable.get(1);
        GameObject leftStick = controllable.get(2);
        rightStick.setX(clown.getX() + (int) (clown.getWidth() * 0.7));
        leftStick.setX(clown.getX() + (int) (clown.getWidth() * 0.18));

        for (GameObject plate : movable) {

            // If the plate is moving
            if (((ImageObject) plate).getType() == MOVING) {
                plate.setY(plate.getY() + 1);

                // If the plate reaches bottom of the screen, reuse it
                if (plate.getY() == getHeight()) {
                    imagePool.releaseObject(plate);
                    plate.setY(-1 * (int) (Math.random() * getHeight()));
                    plate.setX((int) (Math.random() * getWidth()));
                }

                // Check if a plate touches a stick or a plate and add it
                plateTouchesStick(plate);
                // Check if 3 plates of same color are on a stick
                isThreeOfSameColor(rightStickPlates);
                isThreeOfSameColor(leftStickPlates);
            }

            // If plate is catched by the clown
            else if (((ImageObject) plate).getType() == CATCHED) {
                if (rightStickPlates.contains(plate)) {
                    plate.setX(rightStick.getX() - ((ImageObject) plate).getDistFromStick());
                } else if (leftStickPlates.contains(plate)) {
                    plate.setX(leftStick.getX() - ((ImageObject) plate).getDistFromStick());
                }
            }

            // If plate is falling from the clown
            else if (((ImageObject) plate).getType() == FALLING) {
                plate.setY(plate.getY() + 1);
                if (plate.getY() == getHeight()) {
                    imagePool.releaseObject(plate);
                    plate.setY(-1 * (int) (Math.random() * getHeight()));
                    plate.setX((int) (Math.random() * getWidth()));
                    ((ImageObject) plate).setType(MOVING);
                }
            }
        }
        return !timeout;
    }

    private void plateTouchesStick(GameObject plate) {
        GameObject rightStick = controllable.get(1);
        GameObject leftStick = controllable.get(2);

        // Check if stick is not empty first
        // because we see if the last plate touches the new plate
        if (isRightStickEmpty) {
            if (plate.getY() + plate.getHeight() == rightStick.getY()) {
                int difference = Math.abs(rightStick.getX() - plate.getX() - 27);
                if (difference < differenceBetweenPlateAndStick) {
                    ((ImageObject) plate).setType(CATCHED);
                    ((ImageObject) plate).setDistFromStick(rightStick.getX() - plate.getX());
                    rightStickPlates.add(plate);
                    isRightStickEmpty = false;
                }
            }
        } else {
            if (plate.getY() + plate.getHeight() == rightStickPlates.getLast().getY()) {
                int difference = Math.abs(plate.getX() - (rightStickPlates.getLast().getX()));
                if (difference < differenceBetweenPlateAndPlate) {
                    ((ImageObject) plate).setType(CATCHED);
                    ((ImageObject) plate).setDistFromStick(rightStick.getX() - plate.getX());
                    rightStickPlates.add(plate);
                } else if (difference < plate.getWidth() - 22) {
                    ((ImageObject) plate).setType(FALLING);
                    ((ImageObject) rightStickPlates.getLast()).setType(FALLING);
                    rightStickPlates.remove(rightStickPlates.size() - 1);
                    isRightStickEmpty = rightStickPlates.isEmpty();
                }
            }
        }
        if (isLeftStickEmpty) {
            if (plate.getY() + plate.getHeight() == leftStick.getY()) {
                int difference = Math.abs(leftStick.getX() - plate.getX() - 27);
                if (difference < differenceBetweenPlateAndStick) {
                    ((ImageObject) plate).setType(CATCHED);
                    ((ImageObject) plate).setDistFromStick(leftStick.getX() - plate.getX());
                    leftStickPlates.add(plate);
                    isLeftStickEmpty = false;
                }
            }
        } else {
            if (plate.getY() + plate.getHeight() == leftStickPlates.getLast().getY()) {
                int difference = Math.abs(plate.getX() - (leftStickPlates.getLast().getX()));
                if (difference < differenceBetweenPlateAndPlate) {
                    ((ImageObject) plate).setType(CATCHED);
                    ((ImageObject) plate).setDistFromStick(leftStick.getX() - plate.getX());
                    leftStickPlates.add(plate);
                } else if (difference < plate.getWidth() - 22) {
                    ((ImageObject) plate).setType(FALLING);
                    ((ImageObject) leftStickPlates.getLast()).setType(FALLING);
                    leftStickPlates.remove(leftStickPlates.size() - 1);
                    isLeftStickEmpty = leftStickPlates.isEmpty();
                }
            }
        }
    }

    private void isThreeOfSameColor(LinkedList<GameObject> stickPlates) {
        if (stickPlates.size() < 3) {
            return;
        }

        int counter = 0, len = stickPlates.size();
        BufferedImage[] color = stickPlates.get(len - 1).getSpriteImages();

        // Check the last 3 plates if of same color
        for (int i = len - 1; i >= len - 3; i--) {
            if (Arrays.equals(color, stickPlates.get(i).getSpriteImages())) {
                counter++;
            }
            if (counter == 3) {
                counter = 0;
                if (stickPlates == rightStickPlates) {
                    isRightStickEmpty = removeUpperThreePlates(stickPlates);
                } else {
                    isLeftStickEmpty = removeUpperThreePlates(stickPlates);
                }
                score += 30;
            }
        }
    }

    private boolean removeUpperThreePlates(LinkedList<GameObject> stickPlates) {
        int len = stickPlates.size();
        for (int i = len - 1; i >= len - 3; i--) {
            ((ImageObject) stickPlates.get(i)).setType(MOVING);
            stickPlates.get(i).setX(rand.nextInt(width - movable.get(i).getWidth()));
            stickPlates.get(i).setY(-1 * rand.nextInt(height));
            stickPlates.remove(i);
        }
        return stickPlates.isEmpty();
    }

    @Override
    public List<GameObject> getConstantObjects() {
        return constant;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        return movable;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        return controllable;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getStatus() {
        return "Score= " + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000);
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getControlSpeed() {
        return controlSpeed;
    }
}
