package ui;

// (REFERENCED: https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/3b1c0a249a36fb3deeda52ac8393433c5ad0a9cd/src/
//              main/ca/ubc/cpsc210/bank/ui/TellerApp.java#L3 for runRoundPlayer() and playRound() )

import model.Round;
import model.RoundHistory;
import model.Sound;

import java.util.Objects;
import java.util.Scanner;

import static model.Sound.findSound;

public class RoundPlayer {
    RoundHistory roundHistory;
    Scanner input;

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

        roundHistory.finishRound();

    }

    // EFFECTS: runs a single round
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
        System.out.println("New round -> 'n'");
        System.out.println("View high score -> 'v'");
        System.out.println("View number of rounds played -> 'r'");
        System.out.println("Quit -> 'q'");
    }

    // EFFECTS: prints potential actions when in a round
    public void displayInRoundMenu() {
        playCompleteSoundList(roundHistory.getCurrentRound());
        System.out.println("Recite the pattern (input options):");
        System.out.println("Sound 0 -> '0'");
        System.out.println("Sound 1 -> '1'");
        System.out.println("Sound 2 -> '2'");
        System.out.println("Sound 3 -> '3'");
    }

    // EFFECTS: processes user's commands when not playing a round
    public void processOutRoundCommand(String command) {
        if (Objects.equals(command, "n")) {
            playRound();
        } else if (Objects.equals(command, "v")) {
            System.out.println("High score is: " + roundHistory.getHighScore());
        } else if (Objects.equals(command, "r")) {
            System.out.println("Number of rounds played: " + roundHistory.getNumRoundsPlayed());
        } else {
            System.out.println("Unknown command given. Please input one of 'n'/'v'/'q'.");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the round history
    public void initializeRoundPlayer() {
        roundHistory = new RoundHistory();
        input = new Scanner(System.in);
    }

    // EFFECTS: plays each sound in round's sound list (note: currently prints out strings rather than playing sounds)
    public void playCompleteSoundList(Round round) {
        for (Sound s : round.getSoundList()) {
            System.out.println(s.getLabel());
        }
    }

}