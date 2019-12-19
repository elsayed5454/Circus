package eg.edu.alexu.csd.oop.game.view;

import java.awt.*;

public class ScreenResolution {

    //width of the screen
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    //height of the screen
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    public ScreenResolution () {}

    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
    }
}
