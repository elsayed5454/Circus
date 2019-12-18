package eg.edu.alexu.csd.oop.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eg.edu.alexu.csd.oop.game.world.Circus;

public class Main {
    public static void Main(int gameLevel) {

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(newMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menu.add(exitMenuItem);
        menuBar.add(menu);
        final GameEngine.GameController gameController = GameEngine.start("GAME", new eg.edu.alexu.csd.oop.game.world.Circus((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 14 , (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 54, gameLevel), menuBar, Color.DARK_GRAY);
        newMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                gameController.changeWorld((World) new eg.edu.alexu.csd.oop.game.world.Circus((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(), gameLevel));
            }
        });
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                gameController.pause();
            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                gameController.resume();
            }
        });
    }
}
