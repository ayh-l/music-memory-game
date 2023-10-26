package persistence;

import model.Round;
import model.RoundHistory;
import model.Sound;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// (CODE MODELED ON: JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
// Represents a reader that reads a round history from JSON data in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a JSON reader with given source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads round history from source file and returns it.
    //          throws IOException if error occurs while reading data from file
    public RoundHistory read() throws IOException {
        String fileData = readFile(this.source);
        JSONObject jsonObject = new JSONObject(fileData);
        return parseRoundHistory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(stringBuilder::append);
        }

        return stringBuilder.toString();
    }

    // EFFECTS: parses round history from JSON object and returns it
    private RoundHistory parseRoundHistory(JSONObject jsonObject) {
        RoundHistory rh = new RoundHistory();
        int i = (int) jsonObject.get("highscore");
        rh.setHighScore(i);
        int j = (int) jsonObject.get("num rounds");
        rh.setNumRoundsPlayed(j);

        addRounds(rh, jsonObject);

        return rh;
    }

    // MODIFIES: rh
    // EFFECTS: parses rounds from JSON object and adds them to round history
    private void addRounds(RoundHistory rh, JSONObject jsonObject) {
        JSONArray jsonArray = new JSONArray(jsonObject.get("rounds").toString());
        for (Object roundObject : jsonArray) {
            JSONObject jsonRound = (JSONObject) roundObject;
            addRound(rh, jsonRound);
        }
    }

    // (REFERENCED: https://www.geeksforgeeks.org/convert-a-string-to-a-list-of-characters-in-java/)
    // MODIFIES: rh
    // EFFECTS: parses round from JSON object and adds it to rh
    private void addRound(RoundHistory rh, JSONObject jsonObject) {
        Round round = new Round();
        int i = Integer.parseInt(jsonObject.get("next sound").toString());
        round.setNextCorrectSound(Sound.findSound(i));

        JSONArray soundListLabels = new JSONArray(jsonObject.get("soundlist").toString());
        List<Sound> soundList = new ArrayList<>();
        for (Object o : soundListLabels) {
            soundList.add(Sound.findSound(Integer.parseInt((String) o)));
        }
        round.setSoundList(soundList);

        rh.getCompletedRounds().add(round);
    }

}
