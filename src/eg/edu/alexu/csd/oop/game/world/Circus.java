package eg.edu.alexu.csd.oop.game.world;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

import java.util.List;

public class Circus implements World {
    /**
     * @return list of immovable object
     */
    @Override
    public List<GameObject> getConstantObjects() {
        return null;
    }

    /**
     * @return list of moving object
     */
    @Override
    public List<GameObject> getMovableObjects() {
        return null;
    }

    /**
     * @return list of user controlled object
     */
    @Override
    public List<GameObject> getControlableObjects() {
        return null;
    }

    /**
     * @return screen width
     */
    @Override
    public int getWidth() {
        return 0;
    }

    /**
     * @return screen height
     */
    @Override
    public int getHeight() {
        return 0;
    }

    /**
     * refresh the world state and update locations
     *
     * @return false means game over
     */
    @Override
    public boolean refresh() {
        return false;
    }

    /**
     * status bar content
     *
     * @return string to be shown at status bar
     */
    @Override
    public String getStatus() {
        return null;
    }

    /**
     * @return frequency of calling refresh
     */
    @Override
    public int getSpeed() {
        return 0;
    }

    /**
     * @return frequency of receiving user input
     */
    @Override
    public int getControlSpeed() {
        return 0;
    }
}
