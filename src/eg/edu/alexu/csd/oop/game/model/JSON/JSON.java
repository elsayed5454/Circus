package eg.edu.alexu.csd.oop.game.model.JSON;

import com.google.gson.Gson;
import eg.edu.alexu.csd.oop.game.model.Snapshot.Caretaker;
import eg.edu.alexu.csd.oop.game.model.Snapshot.Originator;
import eg.edu.alexu.csd.oop.game.model.world.Circus;

import java.io.*;

public class JSON {

    public JSON() {

    }

    // Saving the circus object in JSON format
    public void save() {

        Originator originator = Originator.getInstance();
        Caretaker caretaker = Caretaker.getInstance();
        Circus circus = originator.restoreFromMemento(caretaker.getMemento(0));

        // Using Gson library to convert circus to JSON
        Gson gson = new Gson();
        File file = new File("saveFile.json");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(circus.getMovableObjects(), writer);
    }

    // Loading the contents from a JSON file
    public Circus load() {

        // Using Gson library to convert JSON to circus object
        Gson gson = new Gson();
        String path = new File("").getAbsolutePath() + "\\saveFile.json";
        Circus loadedCircus = null;
        try {
            loadedCircus = gson.fromJson(new FileReader(path), Circus.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return loadedCircus;
    }
}
