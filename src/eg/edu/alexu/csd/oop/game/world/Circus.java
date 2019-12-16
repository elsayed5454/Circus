package eg.edu.alexu.csd.oop.game.world;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.objects.ImageObject;

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
    private final int speed = 10; // Frequency
    private final int controlSpeed = 10; // Control Frequency
    private static final int MOVING = 0;    // Plate is moving
    private static final int CATCHED = 1;   // Plate is catched
    private boolean isRightStickEmpty = true;
    private boolean isLeftStickEmpty = true;
    private final Random rand = new Random();

    public Circus(int width, int height) {
        this.width = width;
        this.height = height;

        // The clown
        controllable.add(new ImageObject(0, 0, "/clown.png"));
        controllable.get(0).setX(width / 2 - controllable.get(0).getWidth() / 2);
        controllable.get(0).setY((int) (height * 0.96 - controllable.get(0).getHeight()));
        ((ImageObject)controllable.get(0)).setHorizontalOnly();

        // The two sticks
        int clownHandHeight = controllable.get(0).getY() + (int)(controllable.get(0).getHeight() * 0.01);
        controllable.add(new ImageObject(controllable.get(0).getX() + (int)(controllable.get(0).getWidth() * 0.7), clownHandHeight, "/rightStick.png", true));
        controllable.add(new ImageObject(controllable.get(0).getX() + (int)(controllable.get(0).getWidth() * 0.18), clownHandHeight, "/leftStick.png", true));

        // Plates with random place to appear at and random color
        for (int i = 0; i < 14; i++) {
            movable.add(new ImageObject(0, -1 * rand.nextInt(height), randomPlate(rand.nextInt(3))));
            movable.get(i).setX(rand.nextInt(width - movable.get(i).getWidth()));
        }
    }

    // Deciding the color
    private String randomPlate(int color) {
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

    @Override
    public boolean refresh() {
        // Time ends and game over
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME;

        // Bound sticks to clown position
        GameObject clown = controllable.get(0);
        GameObject rightStick = controllable.get(1);
        GameObject leftStick = controllable.get(2);
        rightStick.setX(clown.getX() + (int)(clown.getWidth() * 0.7));
        leftStick.setX(clown.getX() + (int)(clown.getWidth() * 0.18));

        for(GameObject plate : movable) {
            // If the plate is moving
            if(((ImageObject)plate).getType() == MOVING) {

                plate.setY(plate.getY() + 1);
                // If the plate reaches bottom of the screen, reuse it
                if(plate.getY() == getHeight()) {
                    plate.setY(-1 * (int)(Math.random() * getHeight()));
                    plate.setX((int)(Math.random() * getWidth()));
                }

                // Check if a plate touches a stick or a plate and add it
                plateTouchesStick(plate);
                // Check if 3 plates of same color are on a stick
                isThreeOfSameColor(rightStickPlates);
                isThreeOfSameColor(leftStickPlates);
            }
            else {

                // Set position of plates on each other and on stick
                // or drop upper two plates if recent plate is too far from stick's mid
                int midX = plate.getX() + plate.getWidth() / 2;
                if(rightStickPlates.contains(plate)) {
                    if(midX >= rightStick.getX() && midX <= rightStick.getX() + rightStick.getWidth()) {
                        plate.setX(rightStick.getX() - ((ImageObject) plate).getDistFromStick());
                    }
                    else {
                        isRightStickEmpty = dropFarPlate(rightStickPlates);
                    }
                }
                else if (leftStickPlates.contains(plate)) {
                    if(midX >= leftStick.getX() && midX <= leftStick.getX() + leftStick.getWidth()) {
                        plate.setX(leftStick.getX() - ((ImageObject) plate).getDistFromStick());
                    }
                    else {
                        isLeftStickEmpty = dropFarPlate(leftStickPlates);
                    }
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
        if(!isRightStickEmpty && intersect(plate, rightStickPlates.getLast())) {
            ((ImageObject) plate).setType(CATCHED);
            ((ImageObject) plate).setDistFromStick(rightStick.getX() - plate.getX());
            rightStickPlates.add(plate);
        }
        if(!isLeftStickEmpty && intersect(plate, leftStickPlates.getLast())) {
            ((ImageObject) plate).setType(CATCHED);
            ((ImageObject) plate).setDistFromStick(leftStick.getX() - plate.getX());
            leftStickPlates.add(plate);
        }

        // Check if plate touches the clown stick
        if(isRightStickEmpty && intersect(plate, rightStick)) {
            ((ImageObject) plate).setType(CATCHED);
            ((ImageObject) plate).setDistFromStick(rightStick.getX() - plate.getX());
            rightStickPlates.add(plate);
            isRightStickEmpty = false;
        }
        if(isLeftStickEmpty && intersect(plate, leftStick)) {
            ((ImageObject) plate).setType(CATCHED);
            ((ImageObject) plate).setDistFromStick(leftStick.getX() - plate.getX());
            leftStickPlates.add(plate);
            isLeftStickEmpty = false;
        }
    }

    private boolean intersect(GameObject higherObject, GameObject lowerObject){
        int midX = higherObject.getX() + higherObject.getWidth() / 2;
        return (higherObject.getY() + higherObject.getHeight() == lowerObject.getY()
                && midX >= lowerObject.getX()
                && midX <= lowerObject.getX() + lowerObject.getWidth());
    }

    private void isThreeOfSameColor(LinkedList<GameObject> stickPlates) {
        if (stickPlates.size() < 3) {
            return;
        }

        int counter = 0, len = stickPlates.size();
        String color = ((ImageObject)stickPlates.get(len - 1)).getPath();

        // Check the last 3 plates if of same color
        for(int i = len - 1; i >= len - 3; i--) {
            if(color.equals(((ImageObject)stickPlates.get(i)).getPath())) {
                counter++;
            }
            if(counter == 3) {
                counter = 0;
                if(stickPlates == rightStickPlates) {
                    isRightStickEmpty = removeUpperThreePlates(stickPlates);
                }
                else {
                    isLeftStickEmpty = removeUpperThreePlates(stickPlates);
                }
                score += 30;
            }
        }
    }

    private boolean removeUpperThreePlates(LinkedList<GameObject> stickPlates) {
        int len = stickPlates.size();
        for(int i = len - 1; i >= len - 3; i--) {
            ((ImageObject)stickPlates.get(i)).setType(MOVING);
            stickPlates.get(i).setX(rand.nextInt(width - movable.get(i).getWidth()));
            stickPlates.get(i).setY(-1 * rand.nextInt(height));
            stickPlates.remove(i);
        }
        return stickPlates.isEmpty();
    }

    private boolean dropFarPlate(LinkedList<GameObject> stickPlates) {
        int len = stickPlates.size();
        if(len==1){
            ((ImageObject)stickPlates.get(len - 1)).setType(MOVING);
            stickPlates.remove(len - 1);
        }else {
            ((ImageObject) stickPlates.get(len - 1)).setType(MOVING);
            stickPlates.remove(len - 1);
            ((ImageObject) stickPlates.get(len - 2)).setType(MOVING);
            stickPlates.remove(len - 2);
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
