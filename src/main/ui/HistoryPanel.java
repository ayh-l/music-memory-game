package ui;

// (REFERENCED: B02-SpaceInvadersBase - ScorePanel)

import model.Round;
import model.RoundHistory;
import model.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a history panel that displays data about the completed rounds played so far
public class HistoryPanel extends JPanel implements ActionListener {
    private static final String HIGH_SCORE_TXT = "High score: ";
    private static final String NUM_ROUNDS_TXT = "Number of rounds played: ";
    private static final String ROUND_HISTORY_TXT = "Round history: ";
    private final RoundPlayerVisual roundPlayerVisual;
    private JLabel highScoreLabel;
    private JLabel numRoundsLabel;
    private JLabel roundHistoryLabel;
    private JLabel message;
    private JButton playSoundsButton;
    private JButton scoresAboveButton;
    private JButton resetFilterButton;
    private JButton backToMenuButton;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JTextField textField;
    private int minFilterScore;


    // EFFECTS: constructs a history panel with given round history
    public HistoryPanel(RoundPlayerVisual rpv) {
        roundPlayerVisual = rpv;
        initializeLabels();
        initializeButtonsAndField();
        initializePane();

        displayComponents();
    }

    private void displayComponents() {
        this.removeAll();
        add(backToMenuButton);
        add(Box.createHorizontalStrut(500));
        add(highScoreLabel);
        add(numRoundsLabel);
        add(Box.createHorizontalStrut(500));
        add(playSoundsButton);
        add(scoresAboveButton);
        add(textField);
        add(resetFilterButton);
        add(message);
        add(Box.createHorizontalStrut(500));
        add(roundHistoryLabel);
        add(scrollPane);
    }

    private void initializeButtonsAndField() {
        playSoundsButton = new JButton("Play all sounds");
        scoresAboveButton = new JButton("Filter rounds with scores >= ");
        resetFilterButton = new JButton("Reset rounds filter");
        backToMenuButton = new JButton("Return to menu");
        playSoundsButton.setActionCommand("play sounds");
        scoresAboveButton.setActionCommand("filter");
        resetFilterButton.setActionCommand("undo filter");
        backToMenuButton.setActionCommand("hist: menu");
        playSoundsButton.addActionListener(this);
        scoresAboveButton.addActionListener(this);
        resetFilterButton.addActionListener(this);
        backToMenuButton.addActionListener(roundPlayerVisual);
        playSoundsButton.setPreferredSize(new Dimension(300, 20));

        textField = new JTextField(4);
        textField.addActionListener(this);
    }

    private void initializePane() {
        Dimension preferredPaneSize = new Dimension(400, 300);
        textArea = new JTextArea();
        textArea.setPreferredSize(preferredPaneSize);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(preferredPaneSize);
        resetFilterDisplay();
    }

    private void initializeLabels() {
        RoundHistory roundHistory = roundPlayerVisual.getRoundHistory();
        highScoreLabel = new JLabel(HIGH_SCORE_TXT + roundHistory.getHighScore());
        numRoundsLabel = new JLabel(NUM_ROUNDS_TXT + roundHistory.getNumRoundsPlayed());
        roundHistoryLabel = new JLabel(ROUND_HISTORY_TXT);
        message = new JLabel("");

        highScoreLabel.setPreferredSize(new Dimension(100, 25));
        numRoundsLabel.setPreferredSize(new Dimension(200, 25));
        roundHistoryLabel.setPreferredSize(new Dimension(200, 25));
        message.setPreferredSize(new Dimension(200, 25));
    }

    // MODIFIES: this
    // EFFECTS: updates history panel to display current info
    public void update() {
        RoundHistory roundHistory = roundPlayerVisual.getRoundHistory();
        highScoreLabel.setText(HIGH_SCORE_TXT + roundHistory.getHighScore());
        numRoundsLabel.setText(NUM_ROUNDS_TXT + roundHistory.getNumRoundsPlayed());
        textArea.setText(returnRoundsToDisplay(minFilterScore));
        displayComponents();
    }

    private String returnSoundList(Round round) {
        StringBuilder soundList = new StringBuilder();
        for (Sound s : round.getSoundList()) {
            soundList.append("\n").append(s.getLabel()).append(" ");
        }
        return soundList.toString();
    }

    private String returnRoundsToDisplay(int min) {
        RoundHistory roundHistory = roundPlayerVisual.getRoundHistory();
        roundHistory.filterCompletedRoundsToDisplay(min);
        StringBuilder complete = new StringBuilder();
        for (Round r : roundHistory.getCompletedRoundsToDisplay()) {
            if (r.getSoundList().size() >= min) {
                complete.append("Round ").append(roundHistory.getCompletedRounds().indexOf(r))
                        .append(":").append("\n").append(returnSoundList(r)).append("\n \n");
            }
        }
        return complete.toString();
    }

//    private String returnSoundList(Round round) { //TODO remove
//        StringBuilder soundList = new StringBuilder();
//        for (Sound s : round.getSoundList()) {
//            soundList.append("\n").append(s.getLabel()).append(" ");
//        }
//        return soundList.toString();
//    }
//
//    private String returnAllCompletedRounds(int min) {
//        RoundHistory roundHistory = roundPlayerVisual.getRoundHistory();
//        StringBuilder complete = new StringBuilder();
//        for (Round r : roundHistory.getCompletedRounds()) {
//            if (r.getSoundList().size() >= min) {
//                complete.append("Round ").append(roundHistory.getCompletedRounds().indexOf(r))
//                        .append(":").append("\n").append(returnSoundList(r)).append("\n \n");
//            }
//        }
//        return complete.toString();
//    }

    // MODIFIES: this
    // EFFECTS: according to event, either plays the sound lists of all completed rounds, filters the display of
    //          completed rounds using the set minimum score, or undoes the filter.
    @Override
    public void actionPerformed(ActionEvent e) {
        RoundHistory roundHistory = roundPlayerVisual.getRoundHistory();
        String command = e.getActionCommand();
        if (command.equals("play sounds")) {
            playAllRounds(roundHistory);
        }
        if (command.equals("filter")) {
            textArea.setText(returnRoundsToDisplay(minFilterScore));
            update();
        }
        if (command.equals("undo filter")) {
            resetFilterDisplay();
            update();
        } else {
            try {
                minFilterScore = Integer.parseInt(textField.getText());
            } catch (NumberFormatException nfe) {
                message.setText("Please input an integer");
            }
        }
    }

    private void playAllRounds(RoundHistory roundHistory) {
        SoundPlayer sp = new SoundPlayer();
        for (Round r : roundHistory.getCompletedRoundsToDisplay()) {
            try {
                sp.playSoundList(r);
            } catch (InterruptedException ex) {
                message.setText("Sound playback was interrupted.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: resets history panel to display all completed rounds (removing filter)
    public void resetFilterDisplay() {
        minFilterScore = 1;
        textArea.setText(returnRoundsToDisplay(1));
    }
}
