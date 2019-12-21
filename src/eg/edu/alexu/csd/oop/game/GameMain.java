package eg.edu.alexu.csd.oop.game;

import eg.edu.alexu.csd.oop.game.view.MainMenu;
import eg.edu.alexu.csd.oop.game.view.ScreenResolution;

public class GameMain {

    public static void main(String[] args) {

        //the screen resolution for any use for screen width or height
        ScreenResolution sr = new ScreenResolution();

        //start main menu
        new MainMenu(sr.getWidth(), sr.getHeight());
    }
}
