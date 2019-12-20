package eg.edu.alexu.csd.oop.game.control;

import eg.edu.alexu.csd.oop.game.model.Strategy.IStrategy;
import eg.edu.alexu.csd.oop.game.model.world.Circus;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.view.MenuBar;

import java.util.List;


public class GameMenu {

    private IStrategy strategy;
    private int width;
    private int height;
    private List<String> jars;

    public GameMenu (List<String> jars, IStrategy strategy, int width, int height){
        this.jars = jars;
        this.strategy = strategy;
        this.width = width;
        this.height = height;
    }

    public void start () {

        //generate the circus world
        World circus = new Circus((int) (width * 0.99), (int) (height * 0.94), strategy, jars);

        //generate menu bar for the engine
        new MenuBar(circus, strategy, jars, height, width).generate();
    }
}
