package eg.edu.alexu.csd.oop.game.model.Logger;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
public class GameLogger {
    private static GameLogger instance;
    private FileHandler fileHandler;
    public Logger logger;
    public static GameLogger getInstance() {

        if (GameLogger.instance == null) {

            GameLogger.instance = new GameLogger();
        }

        return GameLogger.instance;
    }

    private GameLogger() {
        File file = new File("logger.txt");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            fileHandler = new FileHandler("logger.txt", true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        logger = Logger.getLogger("MyLog");
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);

    }
}