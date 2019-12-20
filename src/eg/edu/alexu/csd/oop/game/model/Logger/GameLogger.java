package eg.edu.alexu.csd.oop.game.model.Logger;
import java.io.IOException;
import java.util.Objects;
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
        try {
            fileHandler = new FileHandler("logger.txt", true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        logger = Logger.getLogger("MyLog");
        logger.addHandler(Objects.requireNonNull(fileHandler));
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);

    }
}