package eg.edu.alexu.csd.oop.game.view;

import eg.edu.alexu.csd.oop.game.control.GameMenu;
import eg.edu.alexu.csd.oop.game.model.Logger.GameLogger;
import eg.edu.alexu.csd.oop.game.model.Strategy.EasyStrategy;
import eg.edu.alexu.csd.oop.game.model.Strategy.HardStrategy;
import eg.edu.alexu.csd.oop.game.model.Strategy.IStrategy;
import eg.edu.alexu.csd.oop.game.model.Strategy.MediumStrategy;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu {

    private GameLogger gameLogger = GameLogger.getInstance();
    private int width;
    private int height;
    private int[] chosen = {0,0,0,0};
    private IStrategy strategy;

    public MainMenu (int width, int height){
        this.width = width;
        this.height = height;

        start();
    }

    public void start() {
        //the frame that contains the main menu
        JFrame frame = new JFrame();
        //set the menu in fullscreen
        frame.setBounds(0 , 0 , width , height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //label covering the frame for the background
        ImageIcon bgImg = new ImageIcon(getClass().getResource("/background1.jpg"));
        JLabel bg = new JLabel("" ,  bgImg , JLabel.CENTER) ;
        bg.setSize(width, height);
        frame.add(bg);

        ButtonGenerator buttonGenerator = new ButtonGenerator();

        //label works as a button for easy level
        //first button for before hovering
        JLabel easyButton = buttonGenerator.generateFirst("Easy", width / 2 - 110, height / 2 - 87 , 220, 40);
        bg.add(easyButton);

        //second buttons for after hovering
        JLabel easyButton2 = buttonGenerator.generateSecond("Easy", width / 2 - 110, height / 2 - 87);
        bg.add(easyButton2);


        JLabel mediumButton = buttonGenerator.generateFirst("Normal", width / 2 - 110, height / 2 - 42,  220, 40);
        bg.add(mediumButton);

        JLabel mediumButton2 = buttonGenerator.generateSecond("Normal", width / 2 - 110, height / 2 - 42);
        bg.add(mediumButton2);

        JLabel hardButton = buttonGenerator.generateFirst("Hard", width / 2 - 110 , height / 2 + 3 , 220, 40 );
        bg.add(hardButton);

        JLabel hardButton2 = buttonGenerator.generateSecond("Hard", width / 2 - 110 , height / 2 + 3);
        bg.add(hardButton2);

        JLabel loadButton = buttonGenerator.generateFirst("Load", width / 2 - 110, height / 2 + 80 , 220, 40);
        JLabel loadButton2 = buttonGenerator.generateSecond("Load", width / 2 - 110, height / 2 + 80);
        bg.add(loadButton);
        bg.add(loadButton2);



        OptionsMenu optionsMenu = new OptionsMenu(width, height);

        JLabel choose = optionsMenu.choose();
        bg.add(choose);

        JLabel[] choices = optionsMenu.choices();
        for (int i = 0 ; i < choices.length ; i++){
            bg.add(choices[i]);
        }

        JLabel start = buttonGenerator.generateFirst("START", width / 2 - 110, height / 2 + 190, 220, 40);
        start.setSize(0,0);
        JLabel start2 = buttonGenerator.generateSecond("START", width / 2 - 110, height / 2 + 190);
        bg.add(start);
        bg.add(start2);

        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loadButton.setSize(0,0);
                loadButton2.setSize(220,40);
            }
        });

        //when the mouse hovers out the button the second button vanishes and the first appears
        loadButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (mediumButton.getWidth() != 0 && hardButton.getWidth() != 0 && easyButton.getWidth() != 0) {
                    loadButton2.setSize(0, 0);
                    loadButton2.setSize(220, 40);
                }
            }

            //start the game at easy level
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        //when the mouse is hovering make the first button vanish and the second button appears
        easyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                easyButton.setSize(0,0);
                easyButton2.setSize(220,40);
            }
        });

        //when the mouse hovers out the button the second button vanishes and the first appears
        easyButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (mediumButton.getWidth() != 0 && hardButton.getWidth() != 0) {
                    easyButton2.setSize(0, 0);
                    easyButton.setSize(220, 40);
                }
            }

            //start the game at easy level
            @Override
            public void mouseClicked(MouseEvent e) {
                easyButton2.setSize(0, 0);
                mediumButton.setSize(0, 0);
                hardButton.setSize(0, 0);
                loadButton.setSize(0,0);
                choose.setSize(800,60);
                for (int i = 0 ; i < choices.length ; i++){
                    choices[i].setSize(90, 100);
                }
                start.setSize(220,40);
                strategy = new EasyStrategy();
                gameLogger.logger.info(" the game is in Easy Mode ^_^ ");
            }
        });

        mediumButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mediumButton.setSize(0,0);
                mediumButton2.setSize(220,40);
            }
        });

        mediumButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (easyButton.getWidth() != 0 && hardButton.getWidth() != 0) {
                    mediumButton2.setSize(0, 0);
                    mediumButton.setSize(220, 40);
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                easyButton.setSize(0,0);
                mediumButton2.setSize(0, 0);
                hardButton.setSize(0,0);
                loadButton.setSize(0,0);
                choose.setSize(800,60);
                for (int i = 0 ; i < choices.length ; i++){
                    choices[i].setSize(90, 100);
                }
                start.setSize(220,40);
                strategy = new MediumStrategy();
                gameLogger.logger.info(" the game is in Medium Mode -_- ");
            }
        });

        hardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hardButton.setSize(0,0);
                hardButton2.setSize(220,40);
            }
        });

        hardButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (easyButton.getWidth() != 0 && mediumButton.getWidth() != 0) {
                    hardButton2.setSize(0, 0);
                    hardButton.setSize(220, 40);
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                easyButton.setSize(0,0);
                mediumButton.setSize(0,0);
                hardButton2.setSize(0, 0);
                loadButton.setSize(0,0);
                choose.setSize(800,60);
                for (int i = 0 ; i < choices.length ; i++){
                    choices[i].setSize(90, 100);
                }
                start.setSize(220,40);
                strategy = new HardStrategy();
                gameLogger.logger.info(" the game is in Hard Mode '_' ");
            }
        });

        JLabel exit = buttonGenerator.generateFirst("Exit", width - 280, height - 100, 220, 40);
        JLabel exit2 = buttonGenerator.generateSecond("Exit", width - 280, height - 100);
        bg.add(exit);
        bg.add(exit2);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exit.setSize(0, 0);
                exit2.setSize(220, 40);
            }
        });

        exit2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                exit2.setSize(0,0);
                exit.setSize(220,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        optionsMenu.chosen(choices, chosen);

        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                start.setSize(0, 0);
                start2.setSize(220, 40);
            }
        });

        start2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                start2.setSize(0,0);
                start.setSize(220,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                optionsMenu.start(chosen, strategy, frame);
                gameLogger.logger.info(" The Game starts successfully ^_^ ");
            }
        });

        //set fullscreen
        frame.setUndecorated(true);
        //make the frame visible
        frame.setVisible(true);
    }
}
