package model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

// Represents a history of completed rounds and relevant information
public class RoundHistory {
    private final List<Round> completedRounds;      // a list of all completed rounds
    private List<Round> completedRoundsToDisplay;   // a list of completed rounds to display (may be filtered)
    private int highScore;                          // the highest score contained in the list of rounds
    private Round currentRound;                     // the current round (if any)
    private int numRoundsPlayed;                    // total number of rounds played

    // EFFECTS: constructs a new round history with an empty list of rounds, high score set to 0, no current round,
    //          and 0 total rounds played.
    public RoundHistory() {
        completedRounds = new ArrayList<>();
        completedRoundsToDisplay = completedRounds;
        highScore = 0;
        currentRound = null;
        numRoundsPlayed = 0;
    }

    // REQUIRES: must follow an incorrect recitation of sound list
    // MODIFIES: this
    // EFFECTS: adds current round to this round history's completed rounds and removes it from the current round.
    //          Updates high score if applicable, and adds 1 to the total number of rounds played.
    public void finishRound() {
        this.addRound(this.currentRound);
        EventLog.getInstance().logEvent(new Event("Added a round with score "
                + this.currentRound.getSoundList().size()));
        this.currentRound = null;
        this.numRoundsPlayed++;
    }

    // REQUIRES: round is a completed round
    // MODIFIES: this
    // EFFECTS: add this to the list of rounds, and updates overall high score if applicable
    private void addRound(Round round) {
        this.completedRounds.add(round);
        if (round.getSoundList().size() > this.highScore) {
            this.highScore = round.getSoundList().size();
        }
    }

    // REQUIRES: this round history has no current round
    // MODIFIES: this
    // EFFECTS: creates a new round and sets it as this round history's current round
    public void startNewRound() {
        Round newRound = new Round();
        this.setCurrentRound(newRound);
    }

    // REQUIRES: int is <= number of completed rounds
    // EFFECTS: returns the round at position int in the list of completed rounds
    public Round selectCompletedRound(int i) {
        return completedRounds.get(i);
    }

    // (REFERENCED: JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
    // EFFECTS: returns the round history (completed rounds, high score, num rounds played, current round) as JSON array
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("highscore", highScore);
        json.put("num rounds", numRoundsPlayed);
        json.put("rounds", roundsToJson());
        return json;
    }

    // (REFERENCED: JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
    // EFFECTS: returns list of completed rounds as a JSON array
    private JSONArray roundsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Round r : completedRounds) {
            jsonArray.put(r.roundToJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: sets the completed rounds to display as a list of completed rounds >= min
    public void filterCompletedRoundsToDisplay(int min) {
        List<Round> toDisplay = new ArrayList<>();
        resetCompletedRoundsToDisplay();
        for (Round r : completedRoundsToDisplay) {
            if (r.getSoundList().size() >= min) {
                toDisplay.add(r);
            }
        }
        completedRoundsToDisplay = toDisplay;
        EventLog.getInstance().logEvent(new Event("Filtered out rounds with scores below " + min));
    }

    // MODIFIES: this
    // EFFECTS: resets completed rounds to display as the total list of completed rounds
    private void resetCompletedRoundsToDisplay() {
        this.completedRoundsToDisplay = this.completedRounds;
        EventLog.getInstance().logEvent(new Event("Filter was reset"));
    }

    // MODIFIES: this
    // EFFECTS: resets this round history to state at intialization
    public void reset() {
        completedRounds.clear();
        completedRoundsToDisplay = completedRounds;
        highScore = 0;
        currentRound = null;
        numRoundsPlayed = 0;
        EventLog.getInstance().logEvent(new Event("All rounds were removed from history"));
    }

    //setters
    public void setHighScore(int i) {
        this.highScore = i;
    }

    public void setCurrentRound(Round r) {
        this.currentRound = r;
    }

    public void setNumRoundsPlayed(int i) {
        this.numRoundsPlayed = i;
    }

    //getters
    public List<Round> getCompletedRoundsToDisplay() {
        return this.completedRoundsToDisplay;
    }

    public List<Round> getCompletedRounds() {
        return this.completedRounds;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public Round getCurrentRound() {
        return this.currentRound;
    }

    public int getNumRoundsPlayed() {
        return this.numRoundsPlayed;
    }
}
