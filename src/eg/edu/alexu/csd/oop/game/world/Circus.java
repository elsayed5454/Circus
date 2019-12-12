package eg.edu.alexu.csd.oop.game.world;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.objects.BarObject;
import eg.edu.alexu.csd.oop.game.objects.ImageObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Circus implements World {

    private final List<GameObject> constant = new LinkedList<GameObject>(); //non moving objects in the game
    private final List<GameObject> movable = new LinkedList<GameObject>(); //auto moving objects in the game
    private final List<GameObject> controllable = new LinkedList<GameObject>(); //objects that the user control its movement in the game
    private final int width ; //the width of the screen
    private final int height ; //the height of the screen
    private int score = 0 ; //current score
    private final int MAX_TIME = 1 * 60 * 1000 ; //starting time 1 minute
    private final long startTime = System.currentTimeMillis() ; //the system time when the game starts
    private final int speed = 10 ; //frequency

    public Circus (int width , int height) {
        this.width = width ;
        this.height = height ;

        Random rand = new Random() ;
        // plates with random place to appear at and random color
        for (int i = 0 ; i < 7 ; i++) {
            this.movable.add(new ImageObject(  rand.nextInt( width + 1 ), 0 , randomPlate(rand.nextInt( 3 ))));
        }
        //a clown
        this.controllable.add(new ImageObject( width / 2 , height , "clown.png")) ;

    }

    //deciding the color
    private String randomPlate (int color) {
        switch (color) {
            case 0 :
                return "red.png";
            case 1 :
                return "blue.png";
            case 2 :
                return "green.png";
        }
        return null;
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
        return speed;
    }

    @Override
    public int getControlSpeed() {
        return speed;
    }
}
