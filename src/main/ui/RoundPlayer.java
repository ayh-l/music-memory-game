package ui;

// (REFERENCED: https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/3b1c0a249a36fb3deeda52ac8393433c5ad0a9cd/src/
//              main/ca/ubc/cpsc210/bank/ui/TellerApp.java#L3 for runRoundPlayer() and playRound() )

import model.Round;
import model.RoundHistory;
import model.Sound;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static model.Sound.findSound;

// Represents the round player application
public class RoundPlayer {
    private static final String JSON_STORE = "./data/roundHistory.json";
    private RoundHistory roundHistory;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public RoundPlayer() {
        runRoundPlayer();
    }


    // EFFECTS: runs player application
    public void runRoundPlayer() {
        boolean keepGoing = true;
        initializeRoundPlayer();

        while (keepGoing) {
            displayMainMenu();
            String command = input.next();
            if (Objects.equals(command, "q")) {
                keepGoing = false;
            } else {
                processOutRoundCommand(command);
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: initializes the round history
    private void initializeRoundPlayer() {
        roundHistory = new RoundHistory();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: runs a single round. adds the round to the round history's played rounds and updates number of rounds
    //          played and high score (if necessary).
    public void playRound() {
        boolean isRoundOver;
        roundHistory.startNewRound();

        displayInRoundMenu();
        isRoundOver = makeGuess();

        while (!isRoundOver) {
            roundHistory.getCurrentRound().setNextCorrectSound();
            displayInRoundMenu();
            isRoundOver = makeGuess();
        }

        roundHistory.finishRound();
        System.out.println("Incorrect - game over!");
    }

    // EFFECTS: runs a single guess, returning false if guess was correct (meaning game is not over)
    public boolean makeGuess() {
        int count = 0;
        String guess;
        boolean noMistakes = true;
        while ((count < roundHistory.getCurrentRound().getSoundList().size()) && noMistakes) {
            guess = input.next();
            noMistakes = roundHistory.getCurrentRound().guessSound(count, findSound(Integer.parseInt(guess)));
            count++;
        }
        return !noMistakes;
    }

    // EFFECTS: prints potential actions when not in a round
    public void displayMainMenu() {
        System.out.println("Main Menu (input one of):");
        System.out.println("'n' -> New round");
        System.out.println("'v' -> View high score");
        System.out.println("'r' -> View number of rounds played");
        System.out.println("'c' -> View completed rounds");
        System.out.println("'s' -> Save round history to file");
        System.out.println("'l' -> Load round history from file");
        System.out.println("'q' -> Quit");
    }

    // EFFECTS: prints potential actions when in a round
    public void displayInRoundMenu() {
        playSoundList(roundHistory.getCurrentRound());
        System.out.println("Recite the pattern (input options):");
        System.out.println("'0' -> Sound 0");
        System.out.println("'1' -> Sound 1");
        System.out.println("'2' -> Sound 2");
        System.out.println("'3' -> Sound 3");
    }

    // EFFECTS: processes user's commands when not playing a round
    public void processOutRoundCommand(String command) {
        if (Objects.equals(command, "n")) {
            playRound();
        } else if (Objects.equals(command, "v")) {
            System.out.println("High score is: " + roundHistory.getHighScore());
        } else if (Objects.equals(command, "r")) {
            System.out.println("Number of rounds played: " + roundHistory.getNumRoundsPlayed());
        } else if (Objects.equals(command, "c")) {
            viewAllCompletedRounds();
        } else if (Objects.equals(command, "s")) {
            saveRoundHistory();
        } else if (Objects.equals(command, "l")) {
            loadRoundHistory();
        } else {
            System.out.println("Unknown command given. Please input one of 'n'/'v'/'q'.");
        }
    }

    // EFFECTS: plays each sound in round's sound list (note: currently prints out strings rather than playing sounds)
    public void playSoundList(Round round) {
        for (Sound s : round.getSoundList()) {
            System.out.println(s.getLabel());
        }
    }

    // EFFECTS: prints out a list of all completed rounds and their sound lists
    public void viewAllCompletedRounds() {
        for (Round r : roundHistory.getCompletedRounds()) {
            System.out.println("Round " + roundHistory.getCompletedRounds().indexOf(r) + ":");
            playSoundList(r);
        }
    }

    // EFFECTS: saves round history to file
    private void saveRoundHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(roundHistory);
            jsonWriter.close();
            System.out.println("Saved successfully to file " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Could not write to file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads saved round history from file
    private void loadRoundHistory() {
        try {
            roundHistory = jsonReader.read();
            System.out.println("Successfully loaded from file " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Could not load from file " + JSON_STORE);
        }
    }

}