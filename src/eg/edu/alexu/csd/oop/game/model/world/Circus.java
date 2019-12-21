package eg.edu.alexu.csd.oop.game.model.world;

import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.model.AbstractFactory.ConstantObjectFactory;
import eg.edu.alexu.csd.oop.game.model.AbstractFactory.ControllableObjectFactory;
import eg.edu.alexu.csd.oop.game.model.AbstractFactory.GameObjectFactory;
import eg.edu.alexu.csd.oop.game.model.AbstractFactory.MovableObjectFactory;
import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;
import eg.edu.alexu.csd.oop.game.model.Observer.IObserver;
import eg.edu.alexu.csd.oop.game.model.Observer.Plates;
import eg.edu.alexu.csd.oop.game.model.Observer.Score;
import eg.edu.alexu.csd.oop.game.model.Observer.Time;
import eg.edu.alexu.csd.oop.game.model.Snapshot.Caretaker;
import eg.edu.alexu.csd.oop.game.model.Snapshot.Originator;
import eg.edu.alexu.csd.oop.game.model.State.Caught;
import eg.edu.alexu.csd.oop.game.model.State.Falling;
import eg.edu.alexu.csd.oop.game.model.State.Moving;
import eg.edu.alexu.csd.oop.game.model.Strategy.IStrategy;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.model.Iterator.GameObjectList;
import eg.edu.alexu.csd.oop.game.model.Iterator.IIterator;
import eg.edu.alexu.csd.oop.game.model.Iterator.IList;
import eg.edu.alexu.csd.oop.game.model.Pool.ImagePool;
import eg.edu.alexu.csd.oop.game.model.objects.ControllableObject;
import eg.edu.alexu.csd.oop.game.model.objects.MovableObject;
import eg.edu.alexu.csd.oop.game.view.EndMenu;
import eg.edu.alexu.csd.oop.game.view.ScreenResolution;

import java.util.*;

public class Circus implements World {
    private final int MAX_TIME = 60 * 1000; // 1 minute
    // The system time when the game starts
    private final long startTime = System.currentTimeMillis();
    private final List<GameObject> constant = new LinkedList<>();       // Non moving objects in the game
    private List<GameObject> movable = new LinkedList<>();        // Auto moving objects in the game
    private final List<GameObject> controllable = new LinkedList<>();   // Objects that the user control its movement in the game
    private final LinkedList<GameObject> rightStickPlates = new LinkedList<>();
    private final LinkedList<GameObject> leftStickPlates = new LinkedList<>();
    private final int width;    // The width of the screen
    private final int height;   // The height of the screen

    private int score = 0;      // Current score
    private static final int MOVING = 0;    // Plate is moving
    private static final int CAUGHT = 1;   // Plate is caught
    private static final int FALLING = 2;   // Plate is falling


    private final Random rand = new Random();

    private GameLogger gameLogger = GameLogger.getInstance();
    ImagePool imagePool;
    IStrategy strategy;
    private List<IObserver> observers = new ArrayList<>();
    IObserver scoreObserver, timeObserver, plateObserver;
    Originator originator = Originator.getInstance();
    Caretaker caretaker = Caretaker.getInstance();

    GameObjectFactory movableFactory;

    boolean gameOver = false;

    public Circus(int width, int height, IStrategy strategy, List<String> jars) {
        this.width = width;
        this.height = height;

        movableFactory = MovableObjectFactory.getInstance(jars);
        imagePool = new ImagePool(width, height, 14);

        // Background
        // Factories
        GameObjectFactory constantFactory = new ConstantObjectFactory();
        constant.add(constantFactory.getShape(0, 0, "/circus.jpg"));

        // The clown
        GameObjectFactory controllableFactory = new ControllableObjectFactory();
        controllable.add(controllableFactory.getShape(0, 0, "/clown.png"));
        gameLogger.log.debug(" The Clown is Successfully Created ");
        controllable.get(0).setX(width / 2 - controllable.get(0).getWidth() / 2);
        controllable.get(0).setY((int) (height * 0.96 - controllable.get(0).getHeight()));
        ((ControllableObject) controllable.get(0)).setHorizontalOnly();

        // The two sticks
        int clownHandHeight = controllable.get(0).getY() + (int) (controllable.get(0).getHeight() * 0.01);
        controllable.add(controllableFactory.getShape(controllable.get(0).getX() + (int) (controllable.get(0).getWidth() * 0.7), clownHandHeight, "/rightStick.png"));
        gameLogger.log.debug(" The Right Stick is Successfully Created ");
        controllable.add(controllableFactory.getShape(controllable.get(0).getX() + (int) (controllable.get(0).getWidth() * 0.18), clownHandHeight, "/leftStick.png"));
        gameLogger.log.debug(" The Left Stick is Successfully Created ");

        // Game level constructor according to the strategy
        this.strategy = strategy;

        //observers
        scoreObserver = new Score(this);
        timeObserver = new Time(this);
        plateObserver = new Plates(this);
    }

    @Override
    public boolean refresh() {

        // Time ends and game over
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME;

        // Bound sticks to clown position
        GameObject clown = controllable.get(0);
        GameObject rightStick = controllable.get(1);
        GameObject leftStick = controllable.get(2);
        rightStick.setX(clown.getX() + (int) (clown.getWidth() * 0.82));
        leftStick.setX(clown.getX() + (int) (clown.getWidth() * 0.01));

        movable = imagePool.getInUse();
        IList movableList = new GameObjectList(movable);
        IIterator movableIterator = movableList.createIterator();

        if (!rightStickPlates.isEmpty() && rightStickPlates.getLast().getY() <= 0 && !gameOver) {
            gameOver = true;
            new EndMenu(score, new ScreenResolution().getWidth(), new ScreenResolution().getHeight());
            gameLogger.log.info(" Game Over ");
            return false;
        }

        if (!leftStickPlates.isEmpty() && leftStickPlates.getLast().getY() <= 0 && !gameOver) {
            gameOver = true;
            new EndMenu(score, new ScreenResolution().getWidth(), new ScreenResolution().getHeight());
            gameLogger.log.info(" Game Over ");
            return false;
        }

        while (movableIterator.hasNext()) {
            GameObject plate = movableIterator.currentItem();

            // If the plate is moving
            if (((MovableObject) plate).getState() == MOVING) {
                plate.setY(plate.getY() + 1);

                // If the plate reaches bottom of the screen, reuse it
                if (plate.getY() == getHeight()) {
                    imagePool.releaseToPool(plate);

                }

                // Check if a plate touches a stick or a plate and add it
                plateTouchesStick(plate);
                // Check if 3 plates of same color are on a stick
                isThreeOfSameColor(rightStickPlates);
                isThreeOfSameColor(leftStickPlates);
            }

            // If plate is caught by the clown
            else if (((MovableObject) plate).getState() == CAUGHT) {

                // Set the new x
                if (rightStickPlates.contains(plate)) {
                    plate.setX(rightStick.getX() - ((MovableObject) plate).getDistFromStick());
                } else if (leftStickPlates.contains(plate)) {
                    plate.setX(leftStick.getX() - ((MovableObject) plate).getDistFromStick());
                }
            }

            // If plate is falling from the clown
            else if (((MovableObject) plate).getState() == FALLING) {
                plate.setY(plate.getY() + 1);

                if (plate.getY() == getHeight()) {
                    ((MovableObject) plate).setState(new Moving());
                    imagePool.releaseToPool(plate);
                }
            }
            if (movableIterator.hasNext()) {
                movableIterator.next();
            }
        }
        if (timeout && !gameOver) {
            gameOver = true;
            new EndMenu(score, new ScreenResolution().getWidth(), new ScreenResolution().getHeight());
            gameLogger.log.info(" Game Over ");
        }
        return !timeout;
    }

    private void plateTouchesStick(GameObject plate) {
        GameObject rightStick = controllable.get(1);
        GameObject leftStick = controllable.get(2);

        if (rightStickPlates.isEmpty()) {

            //check if plate touches the right stick
            if (plate.getY() + plate.getHeight() == rightStick.getY()) {

                //the error between the center of the plate and the center of the stick
                int difference = Math.abs(rightStick.getX() - plate.getX() - 27);

                //catch the plate if the difference is acceptable
                if (difference < strategy.differenceBetweenPlateAndStick()) {
                    ((MovableObject) plate).setState(new Caught());
                    ((MovableObject) plate).setDistFromStick(rightStick.getX() - plate.getX());
                    rightStickPlates.add(plate);
                    this.registerOnly(plateObserver);
                    this.notifyRegisteredUsers(1);
                    this.registerall();
                    gameLogger.log.info(" The First " + ((MovableObject) rightStickPlates.get(0)).getColor() + " Plate Touches The Right Stick ");
                }
            }
        } else {
            //check if the plate touches the last plate on the stick
            if (plate.getY() + plate.getHeight() == rightStickPlates.getLast().getY()) {
                //the error between the center of the 2 plates
                int difference = Math.abs(plate.getX() - (rightStickPlates.getLast().getX()));
                //catch the plate if the difference is acceptable
                if (difference < strategy.differenceBetweenPlateAndPlate()) {
                    ((MovableObject) plate).setState(new Caught());
                    this.registerOnly(plateObserver);
                    this.notifyRegisteredUsers(1);
                    this.registerall();
                    ((MovableObject) plate).setDistFromStick(rightStick.getX() - plate.getX());
                    rightStickPlates.add(plate);
                    gameLogger.log.info(" A New " + ((MovableObject) rightStickPlates.get(rightStickPlates.size() - 1)).getColor() + " Plate Touches The Right Stick ");
                    //drop the plates if the difference isn't acceptable
                } else if (difference < plate.getWidth() - 22) {
                    gameLogger.log.info("The " + ((MovableObject) rightStickPlates.get(rightStickPlates.size() - 1)).getColor() + " Plate is dropped");
                    if (score >= 10) {
                        score -= 10;
                    }
                    ((MovableObject) plate).setState(new Falling());
                    ((MovableObject) rightStickPlates.getLast()).setState(new Falling());
                    rightStickPlates.remove(rightStickPlates.size() - 1);
                }
            }
        }
        if (leftStickPlates.isEmpty()) {
            if (plate.getY() + plate.getHeight() == leftStick.getY()) {
                int difference = Math.abs(leftStick.getX() - plate.getX() - 27);
                if (difference < strategy.differenceBetweenPlateAndStick()) {
                    ((MovableObject) plate).setState(new Caught());
                    ((MovableObject) plate).setDistFromStick(leftStick.getX() - plate.getX());
                    leftStickPlates.add(plate);
                    this.registerOnly(plateObserver);
                    this.notifyRegisteredUsers(1);
                    this.registerall();
                    gameLogger.log.info(" The First " + ((MovableObject) leftStickPlates.get(0)).getColor() + " Plate Touches The Left Stick ");
                }
            }
        } else {
            if (plate.getY() + plate.getHeight() == leftStickPlates.getLast().getY()) {
                int difference = Math.abs(plate.getX() - (leftStickPlates.getLast().getX()));
                if (difference < strategy.differenceBetweenPlateAndPlate()) {
                    ((MovableObject) plate).setState(new Caught());
                    ((MovableObject) plate).setDistFromStick(leftStick.getX() - plate.getX());
                    this.registerOnly(plateObserver);
                    this.notifyRegisteredUsers(1);
                    this.registerall();
                    leftStickPlates.add(plate);
                    gameLogger.log.info(" A New " + ((MovableObject) leftStickPlates.get(leftStickPlates.size() - 1)).getColor() + " Plate Touches The Left Stick ");
                } else if (difference < plate.getWidth() - 22) {
                    gameLogger.log.info("The " + ((MovableObject) leftStickPlates.get(leftStickPlates.size() - 1)).getColor() + " Plate is dropped");
                    if (score >= 10) {
                        score -= 10;
                    }
                    ((MovableObject) plate).setState(new Falling());
                    ((MovableObject) leftStickPlates.getLast()).setState(new Falling());
                    leftStickPlates.remove(leftStickPlates.size() - 1);
                }
            }
        }

    }

    private void isThreeOfSameColor(LinkedList<GameObject> stickPlates) {
        if (stickPlates.size() < 3) {
            return;
        }

        int counter = 0, len = stickPlates.size();
        String color = ((MovableObject) stickPlates.get(len - 1)).getColor();
        Object BoolObserver;
        // Check the last 3 plates if of same color
        for (int i = len - 1; i >= len - 3; i--) {
            if (color.equals(((MovableObject) stickPlates.get(i)).getColor())) {
                counter++;
            }
            if (counter == 3) {
                counter = 0;
                if (stickPlates == rightStickPlates) {
                    removeUpperThreePlates(stickPlates);
                    BoolObserver = rightStickPlates.isEmpty();
                } else {
                    removeUpperThreePlates(stickPlates);
                    BoolObserver = leftStickPlates.isEmpty();
                }
                this.registerOnly(plateObserver);
                this.notifyRegisteredUsers(BoolObserver);
                this.registerall();
                score += 30;
                this.registerOnly(scoreObserver);
                this.notifyRegisteredUsers(score);
                this.registerall();
            }
        }
    }

    private void removeUpperThreePlates(LinkedList<GameObject> stickPlates) {
        int len = stickPlates.size();
        for (int i = len - 1; i >= len - 3; i--) {
            ((MovableObject) stickPlates.get(i)).setState(new Moving());
            stickPlates.get(i).setX(rand.nextInt(width - movable.get(i).getWidth()));
            stickPlates.get(i).setY(-1 * rand.nextInt(height));

            // Release this plate back to pool
            imagePool.releaseToPool(stickPlates.get(i));

            stickPlates.remove(i);
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
        observers.add(this.scoreObserver);
        observers.add(this.timeObserver);
        observers.add(this.plateObserver);

    }

    public void notifyRegisteredUsers(Object updatedValue) {
        for (IObserver observer : observers)
            observer.update(updatedValue);
    }

    public void Save() {
        originator.set(this);
        caretaker.addMemento(originator.storeInMemento());
    }

}