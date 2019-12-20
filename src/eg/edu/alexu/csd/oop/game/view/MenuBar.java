package eg.edu.alexu.csd.oop.game.view;

import eg.edu.alexu.csd.oop.game.GameEngine;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;
import eg.edu.alexu.csd.oop.game.model.Strategy.IStrategy;
import eg.edu.alexu.csd.oop.game.model.world.Circus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuBar {
    private GameLogger gameLogger = GameLogger.getInstance();
    private World circus;
    private IStrategy strategy;
    private  int width;
    private int height;
    private List<String> jars;

    public MenuBar (World circus, IStrategy strategy, List<String> jars, int width, int height) {

        this.circus = circus;
        this.strategy = strategy;
        this.width = width;
        this.height = height;
        this.jars = jars ;
    }

    public void generate() {

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

        //option for undo the game
        JMenuItem undoItem = new JMenuItem("Undo");

        //option for redo the game
        JMenuItem redoItem = new JMenuItem("Redo");

        menu.add(newMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menu.add(exitMenuItem);
        menu.add(undoItem);
        menu.add(redoItem);

        menuBar.add(menu);

        final GameEngine.GameController gameController = GameEngine.start("Game", circus, menuBar);

        newMenuItem.addActionListener(e -> {
            gameController.changeWorld(new Circus(width, height, strategy, jars));
            gameLogger.logger.info(" New Game is started ");
        });

        pauseMenuItem.addActionListener(e -> {
            gameController.pause();
            gameLogger.logger.info(" The Game is paused ");
        });

        resumeMenuItem.addActionListener(e -> {
            gameController.resume();
            gameLogger.logger.info(" The Game is resumed");
        });

        exitMenuItem.addActionListener(e -> {
            System.exit(0);
            gameLogger.logger.info(" The User Exits From The Game ");
        });

        undoItem.addActionListener(e -> {
                    setCircus(((Circus) circus).Undo());
                    gameController.changeWorld(circus);
                    gameLogger.logger.info(" Undo Step is Done ");
        });


        redoItem.addActionListener(e -> {
            setCircus(((Circus)circus).Redo());
            gameController.changeWorld(circus);
            gameLogger.logger.info(" Redo Step is Done ");
        });
    }

    public void setCircus(World circus) {
        this.circus = circus;
    }
}
