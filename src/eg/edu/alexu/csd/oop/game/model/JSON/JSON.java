package eg.edu.alexu.csd.oop.game.model.JSON;

import com.google.gson.Gson;
import eg.edu.alexu.csd.oop.game.model.Snapshot.Caretaker;
import eg.edu.alexu.csd.oop.game.model.Snapshot.Originator;
import eg.edu.alexu.csd.oop.game.model.world.Circus;

import java.io.*;

public class JSON {
    private Circus circus;

    public JSON() {
        Originator originator = Originator.getInstance();
        Caretaker caretaker = Caretaker.getInstance();
        circus = originator.restoreFromMemento(caretaker.getMemento(0));
    }

    // Saving the circus object in JSON format
    public void save() {

        // Using Gson library to convert circus to JSON
        Gson gson = new Gson();
        File file = new File("saveFile.json");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(circus, writer);
    }

    // Loading the contents from a JSON file
    public Circus load() {

        // Using Gson library to convert JSON to circus object
        Gson gson = new Gson();
        String path = new File("").getAbsolutePath() + "\\saveFile.json";
        Object object = null;
        try {
            object = gson.toJson(new FileReader(path), Object.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return (Circus)object;
    }
}
