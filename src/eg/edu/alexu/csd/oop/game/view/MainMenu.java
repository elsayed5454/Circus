package eg.edu.alexu.csd.oop.game.view;

import eg.edu.alexu.csd.oop.game.control.GameMenu;
import eg.edu.alexu.csd.oop.game.model.Strategy.EasyStrategy;
import eg.edu.alexu.csd.oop.game.model.Strategy.HardStrategy;
import eg.edu.alexu.csd.oop.game.model.Strategy.MediumStrategy;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu {


    private int width;
    private int height;

    public MainMenu (int width, int height){
        this.width = width;
        this.height = height;
    }

    public void start() {
        //the frame that contains the main menu
        JFrame frame = new JFrame();
        //set the menu in fullscreen
        frame.setBounds(0 , 0 , width , height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //label covering the frame for the background
        ImageIcon bgImg = new ImageIcon(getClass().getResource("/background.jpg"));
        JLabel bg = new JLabel("" ,  bgImg , JLabel.CENTER) ;
        bg.setSize(frame.getWidth(), frame.getHeight());
        frame.add(bg);

        //label works as a button for easy level
        //first button for before hovering
        JLabel easyButton = new JLabel("EASY");
        easyButton.setBounds(width / 2 - 110 , height / 2 - 45 , 220, 40 );
        easyButton.setBackground(new Color(0, 0, 0, 200));
        easyButton.setForeground(Color.WHITE);
        easyButton.setFont(new Font("Tahoma", Font.BOLD,15));
        easyButton.setHorizontalAlignment(SwingConstants.CENTER);
        easyButton.setOpaque(true);
        bg.add(easyButton);

        //second buttons for after hovering
        JLabel easyButton2 = new JLabel("EASY");
        easyButton2.setBounds(width / 2 - 110 , height / 2 - 45 , 220, 40 );
        easyButton2.setSize(0,0);
        easyButton2.setBackground(new Color(169, 169, 169, 200));
        easyButton2.setForeground(new Color(212,175,55));
        easyButton2.setFont(new Font("Tahoma", Font.BOLD,15));
        easyButton2.setHorizontalAlignment(SwingConstants.CENTER);
        easyButton2.setOpaque(true);
        bg.add(easyButton2);

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
                easyButton2.setSize(0,0);
                easyButton.setSize(220,40);
            }

            //start the game at easy level
            @Override
            public void mouseClicked(MouseEvent e) {
                new GameMenu(new EasyStrategy(), width, height).start();
            }
        });

        JLabel mediumButton = new JLabel("MEDIUM");
        mediumButton.setBounds(width / 2 - 110 , height / 2  , 220, 40 );
        mediumButton.setBackground(new Color(0, 0, 0, 200));
        mediumButton.setForeground(Color.WHITE);
        mediumButton.setFont(new Font("Tahoma", Font.BOLD,15));
        mediumButton.setHorizontalAlignment(SwingConstants.CENTER);
        mediumButton.setOpaque(true);
        bg.add(mediumButton);

        JLabel mediumButton2 = new JLabel("MEDIUM");
        mediumButton2.setBounds(width / 2 - 110 , height / 2 , 220, 40 );
        mediumButton2.setSize(0,0);
        mediumButton2.setBackground(new Color(169, 169, 169, 200));
        mediumButton2.setForeground(new Color(212,175,55));
        mediumButton2.setFont(new Font("Tahoma", Font.BOLD,15));
        mediumButton2.setHorizontalAlignment(SwingConstants.CENTER);
        mediumButton2.setOpaque(true);
        bg.add(mediumButton2);


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
                mediumButton2.setSize(0,0);
                mediumButton.setSize(220,40);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                new GameMenu(new MediumStrategy(), width, height).start();
            }
        });


        JLabel hardButton = new JLabel("HARD");
        hardButton.setBounds(width / 2 - 110 , height / 2 + 45 , 220, 40 );
        hardButton.setBackground(new Color(0, 0, 0, 200));
        hardButton.setForeground(Color.WHITE);
        hardButton.setFont(new Font("Tahoma", Font.BOLD,15));
        hardButton.setHorizontalAlignment(SwingConstants.CENTER);
        hardButton.setOpaque(true);
        bg.add(hardButton);

        JLabel hardButton2 = new JLabel("HARD");
        hardButton2.setBounds(width / 2 - 110 , height / 2 + 45 , 220, 40 );
        hardButton2.setSize(0,0);
        hardButton2.setBackground(new Color(169, 169, 169, 200));
        hardButton2.setForeground(new Color(212,175,55));
        hardButton2.setFont(new Font("Tahoma", Font.BOLD,15));
        hardButton2.setHorizontalAlignment(SwingConstants.CENTER);
        hardButton2.setOpaque(true);
        bg.add(hardButton2);

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
                hardButton2.setSize(0,0);
                hardButton.setSize(220,40);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                new GameMenu(new HardStrategy(), width, height).start();
            }
        });

        //set fullscreen
        frame.setUndecorated(true);
        frame.setVisible(true);
    }
}
