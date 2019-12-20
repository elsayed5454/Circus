package eg.edu.alexu.csd.oop.game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EndMenu {

    private int score ;
    private int width ;
    private int height;

    public EndMenu(int score, int width, int height){

        this.score = score ;
        this.width = width;
        this.height = height;

        initialize();
    }

    private void initialize(){

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

        JLabel yourScore  = new JLabel("Your score: " + score);
        yourScore.setBounds(width / 2 - 400, height / 2 - 150, 800, 60);
        yourScore.setForeground(Color.WHITE);
        yourScore.setFont(new Font("Tahoma", Font.BOLD,50));
        yourScore.setHorizontalAlignment(SwingConstants.CENTER);
        bg.add(yourScore);

        frame.setUndecorated(true);
        frame.setVisible(true);

        ButtonGenerator buttonGenerator = new ButtonGenerator();

        JLabel newGame = buttonGenerator.generateFirst("New Game", width / 2 - 400 , height / 2 + 100, 250, 50);
        JLabel newGame2 = buttonGenerator.generateSecond("New Game", width/ 2 - 400, height / 2 + 100);

        JLabel exitGame = buttonGenerator.generateFirst("Exit Game", width / 2 + 150 , height / 2 + 100, 250, 50);
        JLabel exitGame2 = buttonGenerator.generateSecond("Exit Game", width/ 2 + 150, height / 2 + 100);

        bg.add(newGame);
        bg.add(newGame2);
        bg.add(exitGame);
        bg.add(exitGame2);

        newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                newGame.setSize(0, 0);
                newGame2.setSize(250, 50);
            }
        });

        newGame2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                newGame2.setSize(0,0);
                newGame.setSize(250,50);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
                new MainMenu(width, height);
            }
        });

        exitGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitGame.setSize(0, 0);
                exitGame2.setSize(250, 50);
            }
        });

        exitGame2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                exitGame2.setSize(0,0);
                exitGame.setSize(250,50);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

    }
}
