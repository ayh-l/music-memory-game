package model;

import java.util.ArrayList;
import java.util.List;

// Represents a list of completed rounds
public class RoundHistory {
    private final List<Round> completedRounds;   // a list of all completed rounds
    private int highScore;                       // the highest score contained in the list of rounds
    private Round currentRound;                  // the current round (if any)
    private int numRoundsPlayed;                 // total number of rounds played

    // EFFECTS: constructs a new round history with an empty list of rounds, high score set to 0, no current round,
    //          and 0 total rounds played.
    public RoundHistory() {
        completedRounds = new ArrayList<>();
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


    //setters
    public void setHighScore(int i) {
        this.highScore = i;
    }

    public void setCurrentRound(Round r) {
        this.currentRound = r;
    }

    //getters
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
