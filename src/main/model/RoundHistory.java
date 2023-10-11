package model;

import java.util.ArrayList;
import java.util.List;

// Represents a list of completed rounds
public class RoundHistory {
    private final List<Round> rounds;   // a list of all completed rounds
    private int highScore;        // the highest score contained in the list of rounds

    // EFFECTS: constructs a new round history with an empty list of rounds and high score set to 0.
    public RoundHistory() {
        rounds = new ArrayList<>();
        highScore = 0;
    }

    // REQUIRES: round is a completed round
    // MODIFIES: this
    // EFFECTS: add this to the list of rounds, and updates overall high score if applicable
    public void addRound(Round round) {
        this.rounds.add(round);
        if (round.getSoundList().size() > this.highScore) {
            this.highScore = round.getSoundList().size();
        }
    }


    //setters
    public void setHighScore(int i) {
        this.highScore = i;
    }

    //getters
    public List<Round> getRounds() {
        return rounds;
    }

    public int getHighScore() {
        return this.highScore;
    }
}
