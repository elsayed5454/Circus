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
    private static final int MOVING = 0;    // Plate is moving
    private static final int CATCHED = 1;   // Plate is catched
    private boolean isRightStickEmpty = true;
    private boolean isLeftStickEmpty = true;

    public Circus(int width, int height) {
        this.width = width;
        this.height = height;

        // Plates with random place to appear at and random color
        Random rand = new Random();
        for (int i = 0; i < 7; i++) {
            movable.add(new ImageObject(0, 0, randomPlate(rand.nextInt(3))));
            movable.get(i).setX(rand.nextInt(width - movable.get(i).getWidth()));
        }

        // The clown
        controllable.add(new ImageObject(0, 0, "/clown.png"));
        controllable.get(0).setX(width / 2 - controllable.get(0).getWidth() / 2);
        controllable.get(0).setY(height - controllable.get(0).getHeight());

        // The two sticks
        int clownHandHeight = controllable.get(0).getHeight();
        int clownHandDist = (int)(controllable.get(0).getWidth() * 0.74);
        int errorPixels = 8;
        controllable.add(new ImageObject(width / 2 + clownHandDist / 2 - errorPixels, clownHandHeight, "/rightStick.png"));
        controllable.add(new ImageObject(width / 2 - clownHandDist, clownHandHeight, "/leftStick.png"));
    }

    // Deciding the color
    private String randomPlate(int color) {
        switch (color) {
            case 0:
                return "/red.png";
            case 1:
                return "/blue.png";
            case 2:
                return "/green.png";
        }
        return null;
    }

    @Override
    public boolean refresh() {
        // Time ends and game over
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME;

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
                isThreeOfSameColor();
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
            rightStickPlates.add(plate);
        }
        if(!isLeftStickEmpty && intersect(plate, leftStickPlates.getLast())) {
            ((ImageObject) plate).setType(CATCHED);
            leftStickPlates.add(plate);
        }

        // Check if plate touches the clown stick
        if(intersect(plate, rightStick)) {
            ((ImageObject) plate).setType(CATCHED);
            rightStickPlates.add(plate);
            isRightStickEmpty = false;
        }
        if(intersect(plate, leftStick)) {
            ((ImageObject) plate).setType(CATCHED);
            leftStickPlates.add(plate);
            isLeftStickEmpty = false;
        }
    }

    private boolean intersect(GameObject o1, GameObject o2){
        return (Math.abs((o1.getX()+o1.getWidth()/2) - (o2.getX()+o2.getWidth()/2)) <= o1.getWidth()) &&
                (Math.abs((o1.getY()+o1.getHeight()/2) - (o2.getY()+o2.getHeight()/2)) <= o1.getHeight());
    }

    private void isThreeOfSameColor() {
        if (leftStickPlates.size() < 3 && rightStickPlates.size() < 3) {
            return;
        }

        // Check the right stick plates
        int counter = 0;
        String color = ((ImageObject)rightStickPlates.get(0)).getPath();
        for(GameObject plate : rightStickPlates) {
            if(color.equals(((ImageObject)plate).getPath())) {
                counter++;
            }
            if(counter == 3) {
                counter = 0;
                if (removeUpperThreePlates(rightStickPlates)) isRightStickEmpty = true;
                score += 30;
            }
        }

        // Check the left stick plates
        counter = 0;
        color = ((ImageObject)leftStickPlates.get(0)).getPath();
        for(GameObject plate : leftStickPlates) {
            if(color.equals(((ImageObject)plate).getPath())) {
                counter++;
            }
            if(counter == 3) {
                counter = 0;
                if(removeUpperThreePlates(leftStickPlates)) isLeftStickEmpty = true;
                score += 30;
            }
        }
    }

    private boolean removeUpperThreePlates(LinkedList<GameObject> stickPlates) {
        int len = stickPlates.size();
        for(int i = len - 1; i >= len - 4; i--) {
            ((ImageObject)stickPlates.get(i)).setVisible(false);
            ((ImageObject)stickPlates.get(i)).setType(MOVING);
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
        return speed;
    }
}
