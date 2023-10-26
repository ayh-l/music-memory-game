package persistence;

import model.RoundHistory;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// (CODE MODELED ON: JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
// Represents a writer that writes JSON representation of round history
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a JsonWriter with given file destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, or throws FileNotFoundException if destination file
    //          cannot be opened for writing.
    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of round history to destination file
    public void write(RoundHistory rh) {
        JSONObject jsonObject = rh.toJson();
        saveToFile(jsonObject.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        this.writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        this.writer.print(json);
    }
}
