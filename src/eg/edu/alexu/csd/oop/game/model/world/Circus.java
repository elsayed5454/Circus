package eg.edu.alexu.csd.oop.game.model.world;

import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.model.Flyweight.FlyweightImageFactory;
import eg.edu.alexu.csd.oop.game.model.Observer.IObserver;
import eg.edu.alexu.csd.oop.game.model.Observer.Plates;
import eg.edu.alexu.csd.oop.game.model.Observer.Score;
import eg.edu.alexu.csd.oop.game.model.Observer.Time;
import eg.edu.alexu.csd.oop.game.model.State.Caught;
import eg.edu.alexu.csd.oop.game.model.State.Falling;
import eg.edu.alexu.csd.oop.game.model.State.Moving;
import eg.edu.alexu.csd.oop.game.model.Strategy.IStrategy;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.Iterator.GameObjectList;
import eg.edu.alexu.csd.oop.game.model.Iterator.IIterator;
import eg.edu.alexu.csd.oop.game.model.Iterator.IList;
import eg.edu.alexu.csd.oop.game.model.Pool.ImagePool;
import eg.edu.alexu.csd.oop.game.model.objects.ImageObject;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
    private static final int CAUGHT = 1;   // Plate is caught
    private static final int FALLING = 2;   // Plate is falling
    private boolean isRightStickEmpty = true;
    private boolean isLeftStickEmpty = true;

    private final Random rand = new Random();

    private IList movableList;
    private IIterator movableIterator;
    private IList controllableList;
    private IIterator controllableIterator;
    FlyweightImageFactory FlyweightimageFactory;
    ImagePool imagePool;
    IStrategy strategy;
    private List<IObserver> observers = new ArrayList<>();
    IObserver Scoreobserver, Timeobserver, Platesobserver;

    public Circus(int width, int height, IStrategy strategy, List<String> jars) {
        this.width = width;
        this.height = height;
        imagePool = new ImagePool(width, height, jars);
        FlyweightimageFactory = new FlyweightImageFactory(jars);

        //background
        constant.add(FlyweightimageFactory.getImageObject(0, 0, "/circus.jpg"));

        // The clown
        controllable.add(FlyweightimageFactory.getImageObject(0, 0, "/clown.png"));
        controllable.get(0).setX(width / 2 - controllable.get(0).getWidth() / 2);
        controllable.get(0).setY((int) (height * 0.96 - controllable.get(0).getHeight()));
        ((ImageObject) controllable.get(0)).setHorizontalOnly();

        // The two sticks
        int clownHandHeight = controllable.get(0).getY() + (int) (controllable.get(0).getHeight() * 0.01);
        controllable.add(FlyweightimageFactory.getImageObject(controllable.get(0).getX() + (int) (controllable.get(0).getWidth() * 0.7), clownHandHeight, "/rightStick.png"));
        controllable.add(FlyweightimageFactory.getImageObject(controllable.get(0).getX() + (int) (controllable.get(0).getWidth() * 0.18), clownHandHeight, "/leftStick.png"));

        // Plates with random place to appear at and random color
        for (int i = 0; i < 14; i++) {
            //movable.add(imagePool.getGameObject());
            try {
                movable.add(FlyweightImageFactory.getShape(rand.nextInt(width), -1 * rand.nextInt(height), randomColor()));
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        movableList = new GameObjectList(movable);
        controllableList = new GameObjectList(controllable);
        controllableIterator = controllableList.createIterator();

        //game level constructor according to the strategy
        this.strategy = strategy;

        Scoreobserver = new Score(this);
        Timeobserver = new Time(this);
        Platesobserver = new Plates(this);
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

        movableIterator = movableList.createIterator();
        while (movableIterator.hasNext()) {
            GameObject plate = movableIterator.currentItem();
            // If the plate is moving
            if (((ImageObject) plate).getState() == MOVING) {
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

            // If plate is caught by the clown
            else if (((ImageObject) plate).getState() == CAUGHT) {
                //set the new x
                if (rightStickPlates.contains(plate)) {
                    plate.setX(rightStick.getX() - ((ImageObject) plate).getDistFromStick());
                } else if (leftStickPlates.contains(plate)) {
                    plate.setX(leftStick.getX() - ((ImageObject) plate).getDistFromStick());
                }
            }

            // If plate is falling from the clown
            else if (((ImageObject) plate).getState() == FALLING) {
                plate.setY(plate.getY() + 1);
                if (plate.getY() == getHeight()) {
                    imagePool.releaseObject(plate);
                    plate.setY(-1 * (int) (Math.random() * getHeight()));
                    plate.setX((int) (Math.random() * getWidth()));
                    ((ImageObject) plate).setState(new Moving());
                }
            }
            movableIterator.next();
        }
        return !timeout;
    }

    private void plateTouchesStick(GameObject plate) {
        GameObject rightStick = controllable.get(1);
        GameObject leftStick = controllable.get(2);

        if (isRightStickEmpty) {
            //check if plate touches the right stick
            if (plate.getY() + plate.getHeight() == rightStick.getY()) {
                //the error between the center of the plate and the center of the stick
                int difference = Math.abs(rightStick.getX() - plate.getX() - 27);
                //catch the plate if the difference is acceptable
                if (difference < strategy.differenceBetweenPlateAndStick()) {
                    ((ImageObject) plate).setState(new Caught());
                    ((ImageObject) plate).setDistFromStick(rightStick.getX() - plate.getX());
                    rightStickPlates.add(plate);
                    this.registerOnly(Platesobserver);
                    this.notifyRegisteredUsers(1);
                    this.registerall();
                    isRightStickEmpty = false;
                }
            }
        } else {
            //check if the plate touches the last plate on the stick
            if (plate.getY() + plate.getHeight() == rightStickPlates.getLast().getY()) {
                //the error between the center of the 2 plates
                int difference = Math.abs(plate.getX() - (rightStickPlates.getLast().getX()));
                //catch the plate if the difference is acceptable
                if (difference < strategy.differenceBetweenPlateAndPlate()) {
                    ((ImageObject) plate).setState(new Caught());
                    this.registerOnly(Platesobserver);
                    this.notifyRegisteredUsers(1);
                    this.registerall();
                    ((ImageObject) plate).setDistFromStick(rightStick.getX() - plate.getX());
                    rightStickPlates.add(plate);
                    //drop the plates if the difference isn't acceptable
                } else if (difference < plate.getWidth() - 22) {
                    ((ImageObject) plate).setState(new Falling());
                    ((ImageObject) rightStickPlates.getLast()).setState(new Falling());
                    rightStickPlates.remove(rightStickPlates.size() - 1);
                    isRightStickEmpty = rightStickPlates.isEmpty();
                }
            }
        }
        if (isLeftStickEmpty) {
            if (plate.getY() + plate.getHeight() == leftStick.getY()) {
                int difference = Math.abs(leftStick.getX() - plate.getX() - 27);
                if (difference < strategy.differenceBetweenPlateAndStick()) {
                    ((ImageObject) plate).setState(new Caught());
                    ((ImageObject) plate).setDistFromStick(leftStick.getX() - plate.getX());
                    leftStickPlates.add(plate);
                    this.registerOnly(Platesobserver);
                    this.notifyRegisteredUsers(1);
                    this.registerall();
                    isLeftStickEmpty = false;
                }
            }
        } else {
            if (plate.getY() + plate.getHeight() == leftStickPlates.getLast().getY()) {
                int difference = Math.abs(plate.getX() - (leftStickPlates.getLast().getX()));
                if (difference < strategy.differenceBetweenPlateAndPlate()) {
                    ((ImageObject) plate).setState(new Caught());
                    ((ImageObject) plate).setDistFromStick(leftStick.getX() - plate.getX());
                    this.registerOnly(Platesobserver);
                    this.notifyRegisteredUsers(1);
                    this.registerall();
                    leftStickPlates.add(plate);
                } else if (difference < plate.getWidth() - 22) {
                    ((ImageObject) plate).setState(new Falling());
                    ((ImageObject) leftStickPlates.getLast()).setState(new Falling());
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
                this.registerOnly(Platesobserver);
                this.notifyRegisteredUsers(2);
                this.registerall();
                score += 30;
                this.registerOnly(Scoreobserver);
                this.notifyRegisteredUsers(score);
                this.registerall();
            }
        }
    }

    private boolean removeUpperThreePlates(LinkedList<GameObject> stickPlates) {
        int len = stickPlates.size();
        for (int i = len - 1; i >= len - 3; i--) {
            ((ImageObject) stickPlates.get(i)).setState(new Moving());
            stickPlates.get(i).setX(rand.nextInt(width - movable.get(i).getWidth()));
            stickPlates.get(i).setY(-1 * rand.nextInt(height));
            stickPlates.remove(i);
        }
        return stickPlates.isEmpty();
    }

    // Randomizing the color
    private String randomColor() {
        switch (rand.nextInt(9)) {
            case 0:
                return "black";
            case 1:
                return "blue";
            case 2:
                return "cyan";
            case 3:
                return "green";
            case 4:
                return "orange";
            case 5:
                return "pink";
            case 6:
                return "purple";
            case 7:
                return "red";
            default:
                return "yellow";
        }
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
        return strategy.speed();
    }

    @Override
    public int getControlSpeed() {
        return strategy.controlSpeed();
    }

    public List<IObserver> getObservers() {
        return observers;
    }

    public void register(IObserver observer) {
        //GameLogger.getInstance().log.debug("Observer registered");
        observers.add(observer);
    }

    public void registerOnly(IObserver observer) {
        //GameLogger.getInstance().log.debug("Observer registered");
        observers.clear();
        observers.add(observer);
    }

    public void registerall() {
        observers.clear();
        observers.add(this.Scoreobserver);
        observers.add(this.Timeobserver);
        observers.add(this.Platesobserver);

    }

    public void unregister(IObserver observer) {
        //GameLogger.getInstance().log.debug("Observer unregistered");
        observers.remove(observer);
    }

    public void notifyRegisteredUsers(int updatedValue) {
        for (IObserver observer : observers)
            observer.update(updatedValue);
    }

}