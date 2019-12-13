package eg.edu.alexu.csd.oop.game.world;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

import java.util.LinkedList;
import java.util.List;

public class Circus implements World {
    private static int MAX_TIME = 2 * 60 * 1000;    // Two minutes
    private int score = 0;
    private long endTime, startTime = System.currentTimeMillis();
    private final int width;
    private final int height;
    private final List<GameObject> constant = new LinkedList<GameObject>();
    private final List<GameObject> moving = new LinkedList<GameObject>();
    private final List<GameObject> control = new LinkedList<GameObject>();

    public Circus(int screenWidth, int screenHeight) {
        width = screenWidth;
        height = screenHeight;
        // Control objects (Clown)
        //control.add(new ImageObject())
        // Moving objects (Plate)
        //control.add(new ImageObject())
        // Constant objects (Bar)
        //control.add(new ImageObject())
    }

    private boolean intersect(GameObject o1, GameObject o2) {
        return (Math.abs((o1.getX()+o1.getWidth()/2) - (o2.getX()+o2.getWidth()/2)) <= o1.getWidth()) &&
               (Math.abs((o1.getY()+o1.getHeight()/2) - (o2.getY()+o2.getHeight()/2)) <= o1.getHeight());
    }

    @Override
    public boolean refresh() {
        // Time end and game over
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME;

        return false;
    }


    @Override
    public String getStatus() {
        return "Please Use Arrows To Move   |   Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (endTime-startTime))/1000);	// update status
    }

    @Override
    public List<GameObject> getConstantObjects() {
        return null;
    }


    @Override
    public List<GameObject> getMovableObjects() {
        return null;
    }


    @Override
    public List<GameObject> getControlableObjects() {
        return null;
    }

    @Override
    public int getWidth() {
        return 0;
    }


    @Override
    public int getHeight() {
        return 0;
    }





    @Override
    public int getSpeed() {
        return 0;
    }


    @Override
    public int getControlSpeed() {
        return 0;
    }
}
