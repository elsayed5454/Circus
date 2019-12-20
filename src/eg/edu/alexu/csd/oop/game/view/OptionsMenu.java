package eg.edu.alexu.csd.oop.game.view;

import eg.edu.alexu.csd.oop.game.control.GameMenu;
import eg.edu.alexu.csd.oop.game.model.Strategy.IStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class OptionsMenu {

    private int width;
    private int height;

    public OptionsMenu (int width, int height){
        this.width = width ;
        this.height = height ;
    }

    public JLabel choose () {

        JLabel choose = new JLabel("Choose the plate shape");
        choose.setBounds(width / 2 - 400, height / 2 - 250, 0, 0);
        choose.setForeground(Color.WHITE);
        choose.setFont(new Font("Tahoma", Font.BOLD,50));
        choose.setHorizontalAlignment(SwingConstants.CENTER);

        return choose ;
    }

    public JLabel[] choices () {

        JLabel[] choices = new JLabel[4];

        choices[0] = new JLabel("", new ImageIcon(getClass().getResource("/blackplatewithoutbase.png")), JLabel.CENTER);
        choices[0].setBounds(width / 2 - 400, height / 2 - 40 , 0, 0);
        choices[1] = new JLabel("", new ImageIcon(getClass().getResource("/blackplatewithbase.png")), JLabel.CENTER);
        choices[1].setBounds(width / 2 - 170, height / 2 - 40, 0, 0);
        choices[2] = new JLabel("", new ImageIcon(getClass().getResource("/blackplatewithdeepbase.png")), JLabel.CENTER);
        choices[2].setBounds(width / 2 + 60, height / 2 - 40, 0, 0);
        choices[3] = new JLabel("", new ImageIcon(getClass().getResource("/blackpot.png")), JLabel.CENTER);
        choices[3].setBounds(width / 2 + 300, height / 2 - 40, 0, 0);

        return choices ;
    }

    public void chosen (JLabel[] choices, int[] chosen){
        choices[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                choices[0].setIcon(new ImageIcon(getClass().getResource("/redplatewithoutbase.png")));
                chosen[0] = 1 ;
            }
        });
        choices[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                choices[1].setIcon(new ImageIcon(getClass().getResource("/redplatewithbase.png")));
                chosen[1] = 1 ;
            }
        });
        choices[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                choices[2].setIcon(new ImageIcon(getClass().getResource("/redplatewithdeepbase.png")));
                chosen[2] = 1 ;

            }
        });
        choices[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                choices[3].setIcon(new ImageIcon(getClass().getResource("/redpot.png")));
                chosen[3] = 1 ;
            }
        });
    }

    public void start (int[] chosen, IStrategy strategy){
        List<String> jars = new LinkedList<>();
        if (chosen[0] == 1){
            jars.add("plateWithoutBase.jar");
        }
        if (chosen[1] == 1){
            jars.add("plateWithBase.jar");
        }
        if (chosen[2] == 1){
            jars.add("plateWithDeepBase.jar");
        }
        if (chosen[3] == 1){
            jars.add("pot.jar");
        }

        new GameMenu(jars, strategy, width, height).start();
    }
}
