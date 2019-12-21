package eg.edu.alexu.csd.oop.game.view;

import eg.edu.alexu.csd.oop.game.GameEngine;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.model.JSON.JSON;
import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;
import eg.edu.alexu.csd.oop.game.model.Strategy.IStrategy;
import eg.edu.alexu.csd.oop.game.model.world.Circus;

import javax.swing.*;
import java.util.List;

public class MenuBar {
    private GameLogger gameLogger = GameLogger.getInstance();
    private World circus;
    private IStrategy strategy;
    private int width;
    private int height;
    private List<String> jars;

    public MenuBar(World circus, IStrategy strategy, List<String> jars, int width, int height) {

        this.circus = circus;
        this.strategy = strategy;
        this.width = width;
        this.height = height;
        this.jars = jars;

        start();
    }

    public void start() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        //option for creating a new game
        JMenuItem newMenuItem = new JMenuItem("New");

        //option for pausing the current game
        JMenuItem pauseMenuItem = new JMenuItem("Pause");

        //option for resuming the current game
        JMenuItem resumeMenuItem = new JMenuItem("Resume");

        //option for exiting the game
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        //option for saving the game
        JMenuItem saveItem = new JMenuItem("Save");

        menu.add(newMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menu.add(exitMenuItem);
        menu.add(saveItem);

        menuBar.add(menu);

        final GameEngine.GameController gameController = GameEngine.start("Game", circus, menuBar);

        newMenuItem.addActionListener(e -> {
            gameController.changeWorld(new Circus(width, height, strategy, jars));
            gameLogger.log.info(" New Game is started ");
        });

        pauseMenuItem.addActionListener(e -> {
            gameController.pause();
            gameLogger.log.info(" The Game is paused ");
        });

        resumeMenuItem.addActionListener(e -> {
            gameController.resume();
            gameLogger.log.info(" The Game is resumed");
        });

        exitMenuItem.addActionListener(e -> {
            System.exit(0);
            gameLogger.log.info(" The User Exits From The Game ");
        });

        saveItem.addActionListener(e -> {
            ((Circus) circus).Save();
            new JSON().save();
            gameLogger.log.info(" The User saves The Game ");
        });

    }

    public void setCircus(World circus) {
        this.circus = circus;
    }
}
