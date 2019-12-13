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
    private final LinkedList<GameObject> leftStickPlates = new LinkedList<>();
    private final LinkedList<GameObject> rightStickPlates = new LinkedList<>();
    private final int width;    // The width of the screen
    private final int height;   // The height of the screen
    private int score = 0;      // Current score
    private final int speed = 10; // Frequency
    private static final int MOVING = 0;    // Plate is moving
    private static final int CATCHED = 1;   // Plate is catched

    public Circus(int width, int height) {
        this.width = width;
        this.height = height;

        // plates with random place to appear at and random color
        Random rand = new Random();
        for (int i = 0; i < 7; i++) {
            this.movable.add(new ImageObject(rand.nextInt(width + 1), 0, randomPlate(rand.nextInt(3))));
        }
        // The clown
        this.controllable.add(new ImageObject(width / 2, height, "/clown.png"));
        // The two sticks
        // TODO: According to clown, initialize these variables
        int clownHandHeight = 10, clownHandPos = 5;

        this.rightStickPlates.add(new ImageObject(width / 2 + clownHandPos, height - clownHandHeight, "/rightStick.png"));
        this.leftStickPlates.add(new ImageObject(width / 2 - clownHandPos, height - clownHandHeight, "/leftStick.png"));
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
                plate.setX(plate.getX() + Math.random() > 0.5 ? 1 : -1);

                // Check if plate intersects with clown stick
                if(intersect(plate, leftStickPlates.getLast())) {
                    ((ImageObject) plate).setType(CATCHED);
                    leftStickPlates.add(plate);
                }
                else if(intersect(plate, rightStickPlates.getLast())) {
                    ((ImageObject) plate).setType(CATCHED);
                    rightStickPlates.add(plate);
                }

                // Check if 3 plates of same color are on a stick
                isThreeOfSameColor();
            }
        }
        return !timeout;
    }

    private boolean intersect(GameObject o1, GameObject o2){
        if (o1 == null || o2 == null) {
            return false;
        }
        return (Math.abs((o1.getX()+o1.getWidth()/2) - (o2.getX()+o2.getWidth()/2)) <= o1.getWidth()) && (Math.abs((o1.getY()+o1.getHeight()/2) - (o2.getY()+o2.getHeight()/2)) <= o1.getHeight());
    }

    private void isThreeOfSameColor() {
        if (leftStickPlates.size() < 3 && rightStickPlates.size() < 3) {
            return;
        }

        // Check the left stick plates
        int counter = 0;
        String color = ((ImageObject)leftStickPlates.get(0)).getPath();
        for(GameObject plate : leftStickPlates) {
            if(color.equals(((ImageObject)plate).getPath())) {
                counter++;
            }
            if(counter == 3) {
                counter = 0;
                removeUpperThreePlates(leftStickPlates);
                score += 30;
            }
        }

        // Check the right stick plates
        counter = 0;
        color = ((ImageObject)rightStickPlates.get(0)).getPath();
        for(GameObject plate : rightStickPlates) {
            if(color.equals(((ImageObject)plate).getPath())) {
                counter++;
            }
            if(counter == 3) {
                counter = 0;
                removeUpperThreePlates(rightStickPlates);
                score += 30;
            }
        }
    }

    private void removeUpperThreePlates(LinkedList<GameObject> stickPlates) {
        int len = stickPlates.size();
        for(int i = len - 1; i >= len - 4; i--) {
            ((ImageObject)stickPlates.get(i)).setVisible(false);
            ((ImageObject)stickPlates.get(i)).setType(MOVING);
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
        return speed;
    }

    @Override
    public int getControlSpeed() {
        return speed;
    }
}
