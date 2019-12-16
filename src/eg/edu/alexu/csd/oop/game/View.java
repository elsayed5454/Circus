package eg.edu.alexu.csd.oop.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class View
{
    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    View window = new View();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public View() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(0 , 0 , Toolkit.getDefaultToolkit().getScreenSize().width , Toolkit.getDefaultToolkit().getScreenSize().height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImageIcon bgImg = new ImageIcon(getClass().getResource("/background.jpg"));
        JLabel bg = new JLabel("" ,  bgImg , JLabel.CENTER) ;
        bg.setSize(frame.getWidth(), frame.getHeight());
        frame.add(bg);

        JLabel easyButton = new JLabel("EASY");
        easyButton.setBounds(frame.getWidth() / 2 - 110 , frame.getHeight() / 2 - 45 , 220, 40 );
        easyButton.setBackground(new Color(0, 0, 0, 200));
        easyButton.setForeground(Color.WHITE);
        easyButton.setFont(new Font("Tahoma", Font.BOLD,15));
        easyButton.setHorizontalAlignment(SwingConstants.CENTER);
        easyButton.setOpaque(true);
        bg.add(easyButton);

        JLabel easyButton2 = new JLabel("EASY");
        easyButton2.setBounds(frame.getWidth() / 2 - 110 , frame.getHeight() / 2 - 45 , 220, 40 );
        easyButton2.setSize(0,0);
        easyButton2.setBackground(new Color(169, 169, 169, 200));
        easyButton2.setForeground(new Color(212,175,55));
        easyButton2.setFont(new Font("Tahoma", Font.BOLD,15));
        easyButton2.setHorizontalAlignment(SwingConstants.CENTER);
        easyButton2.setOpaque(true);
        bg.add(easyButton2);

        easyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                easyButton.setSize(0,0);
                easyButton2.setSize(220,40);
            }
        });

        easyButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                easyButton2.setSize(0,0);
                easyButton.setSize(220,40);
            }
        });

        JLabel mediumButton = new JLabel("MEDIUM");
        mediumButton.setBounds(frame.getWidth() / 2 - 110 , frame.getHeight() / 2  , 220, 40 );
        mediumButton.setBackground(new Color(0, 0, 0, 200));
        mediumButton.setForeground(Color.WHITE);
        mediumButton.setFont(new Font("Tahoma", Font.BOLD,15));
        mediumButton.setHorizontalAlignment(SwingConstants.CENTER);
        mediumButton.setOpaque(true);
        bg.add(mediumButton);

        JLabel mediumButton2 = new JLabel("MEDIUM");
        mediumButton2.setBounds(frame.getWidth() / 2 - 110 , frame.getHeight() / 2 , 220, 40 );
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
        });


        JLabel hardButton = new JLabel("HARD");
        hardButton.setBounds(frame.getWidth() / 2 - 110 , frame.getHeight() / 2 + 45 , 220, 40 );
        hardButton.setBackground(new Color(0, 0, 0, 200));
        hardButton.setForeground(Color.WHITE);
        hardButton.setFont(new Font("Tahoma", Font.BOLD,15));
        hardButton.setHorizontalAlignment(SwingConstants.CENTER);
        hardButton.setOpaque(true);
        bg.add(hardButton);

        JLabel hardButton2 = new JLabel("HARD");
        hardButton2.setBounds(frame.getWidth() / 2 - 110 , frame.getHeight() / 2 + 45 , 220, 40 );
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
        });


        frame.setUndecorated(true);
        frame.setVisible(true);
    }
}
