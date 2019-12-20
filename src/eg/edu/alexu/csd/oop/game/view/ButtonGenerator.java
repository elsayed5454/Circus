package eg.edu.alexu.csd.oop.game.view;

import javax.swing.*;
import java.awt.*;

//generate buttons for my frame
public class ButtonGenerator {

    //button used before hovering
    public JLabel generateFirst (String name, int x, int y, int width, int height){

        JLabel button = new JLabel(name);
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(0, 0, 0, 200));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD,15));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setOpaque(true);

        return button;
    }

    //button used while hovering
    public JLabel generateSecond (String name, int x, int y){

        JLabel button = new JLabel(name);
        button.setBounds(x, y, 0, 0);
        button.setBackground(new Color(169, 169, 169, 200));
        button.setForeground(new Color(212,175,55));
        button.setFont(new Font("Tahoma", Font.BOLD,15));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setOpaque(true);

        return button;
    }
}
