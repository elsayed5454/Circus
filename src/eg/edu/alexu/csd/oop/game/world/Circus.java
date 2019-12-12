package eg.edu.alexu.csd.oop.game.world;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

import java.util.LinkedList;
import java.util.List;

public class Circus implements World {

    private final List<GameObject> constant = new LinkedList<GameObject>(); //non moving objects in the game
    private final List<GameObject> movable = new LinkedList<GameObject>(); //auto moving objects in the game
    private final List<GameObject> controlable = new LinkedList<GameObject>(); //objects that the user control its movement in the game
    private final int width ; //the width of the screen
    private final int height ; //the height of the screen
    private int score = 0 ; //current score
    private final int MAX_TIME = 1 * 60 * 1000 ; //starting time 1 minute
    private final long startTime = System.currentTimeMillis() ; //the system time when the game starts
    private final int speed = 10 ; //frequency

    public Circus (int width , int height) {
        this.width = width ;
        this.height = height ;
        
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
    public boolean refresh() {
        return false;
    }

    @Override
    public String getStatus() {
        String status = "Score= " + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis()-startTime))/1000);
        return status ;
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
