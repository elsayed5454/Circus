package eg.edu.alexu.csd.oop.game.model.Logger;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
public class GameLogger {
    public static void main(String[] args) {

        Logger logger = Logger.getLogger("MyLog");
        FileHandler fileHandler = null;
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
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.info("My first log");
            logger.setUseParentHandlers(false);
            logger.info("Hi How r u?");

        }
    }