package ui;

import model.Round;
import model.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Represents game panel with four sound input buttons, a label that displays messages, and its associated round player
// and sound player
public class GamePanel extends JPanel implements ActionListener {
    private JButton soundZeroButton;
    private JButton soundOneButton;
    private JButton soundTwoButton;
    private JButton soundThreeButton;
    private JButton makeGuessButton;
    private final JLabel correctLabel;
    private final RoundPlayerVisual roundPlayerVisual;
    private final SoundPlayer soundPlayer;

    private boolean inGuess;
    private int guessCount;

    // EFFECTS: Constructs a new game panel, initializes its components and displays them
    public GamePanel(RoundPlayerVisual rpv) {
        roundPlayerVisual = rpv;
        soundPlayer = new SoundPlayer();
        setPreferredSize(new Dimension(500, 500));
        initializeButtons();
        correctLabel = new JLabel("");
        this.setLayout(new BorderLayout());
        add(soundZeroButton, BorderLayout.PAGE_START);
        add(soundOneButton, BorderLayout.LINE_START);
        add(soundTwoButton, BorderLayout.CENTER);
        add(soundThreeButton, BorderLayout.LINE_END);

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(correctLabel);
        panel.add(makeGuessButton);
        add(panel, BorderLayout.PAGE_END);

        inGuess = false;
    }

    // MODIFIES: this
    // EFFECTS: resets this game panel for the start of a new round
    public void reset() {
        correctLabel.setText("");
        guessCount = 0;

        roundPlayerVisual.getRoundHistory().startNewRound();
        playSoundList();
    }

    private void initializeButtons() {
        ImageIcon soundZeroImage = new ImageIcon("./data/trumpet.png");
        ImageIcon soundOneImage = new ImageIcon("./data/saxophone.png");
        ImageIcon soundTwoImage = new ImageIcon("./data/tuba.png");
        ImageIcon soundThreeImage = new ImageIcon("./data/violin.png");
        soundZeroButton = initializeButton(soundZeroImage);
        soundOneButton = initializeButton(soundOneImage);
        soundTwoButton = initializeButton(soundTwoImage);
        soundThreeButton = initializeButton(soundThreeImage);
        soundZeroButton.setActionCommand("sound 0");
        soundOneButton.setActionCommand("sound 1");
        soundTwoButton.setActionCommand("sound 2");
        soundThreeButton.setActionCommand("sound 3");

        makeGuessButton = new JButton("Make guess!");
        makeGuessButton.setActionCommand("guess");
        makeGuessButton.addActionListener(this);
        makeGuessButton.setPreferredSize(new Dimension(100, 50));
    }

    private JButton initializeButton(ImageIcon imageIcon) {
        Dimension preferredSize = new Dimension(100, 100);
        ImageIcon playImage = new ImageIcon("./data/music-note.png");
        JButton button = new JButton(imageIcon);
        button.setPreferredSize(preferredSize);
        button.setPressedIcon(playImage);
        button.addActionListener(this);
        button.addActionListener(soundPlayer);
        return button;
    }

    // EFFECTS: plays sound list of current round
    public void playSoundList() {
        Round r = roundPlayerVisual.getRoundHistory().getCurrentRound();
        for (Sound s : r.getSoundList()) {
            try {
                playSound(s);
            } catch (InterruptedException e) {
                correctLabel.setText("Sound playback was interrupted.");
            }
        }
    }

    private void playSound(Sound s) throws InterruptedException {
        int label = s.getLabel();
        JButton button = null;
        Thread.sleep(500);
        if (label == 0) {
            button = soundZeroButton;
        }
        if (label == 1) {
            button = soundOneButton;
        }
        if (label == 2) {
            button = soundTwoButton;
        }
        if (label == 3) {
            button = soundThreeButton;
        }
        if (button != null) {
            button.doClick();
        }
    }

    // MODIFIES: this
    // EFFECTS: if guess is currently being made, checks sound button inputs. Either ends the round if incorrect
    //          and returns to menu panel, or if correct, plays the next sounds.
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        List<Sound> soundList = roundPlayerVisual.getRoundHistory().getCurrentRound().getSoundList();
        if (inGuess) {
            if ((command.equals("sound 0") && (soundList.get(guessCount).getLabel() == 0))
                    || (command.equals("sound 1") && (soundList.get(guessCount).getLabel() == 1))
                    || (command.equals("sound 2") && (soundList.get(guessCount).getLabel() == 2))
                    || (command.equals("sound 3") && (soundList.get(guessCount).getLabel() == 3))) {
                correctGuess(soundList);
            } else if ((soundList.get(guessCount).getLabel() == 0) || (soundList.get(guessCount).getLabel() == 1)
                    || (soundList.get(guessCount).getLabel() == 2) || (soundList.get(guessCount).getLabel() == 3)) {
                incorrectGuess();
            }
        }
        if (command.equals("guess")) {
            inGuess = true;
        }
    }

    private void incorrectGuess() {
        inGuess = false;
        roundPlayerVisual.getRoundHistory().finishRound();
        correctLabel.setText("Incorrect - game over!");
        roundPlayerVisual.actionPerformed(new ActionEvent(this, 0, "game: menu"));
    }

    private void correctGuess(List<Sound> soundList) {
        guessCount++;
        if (guessCount == soundList.size()) {
            inGuess = false;
            guessCount = 0;
            roundPlayerVisual.getRoundHistory().getCurrentRound().setNextCorrectSound();
            playSoundList();
        }
    }
}