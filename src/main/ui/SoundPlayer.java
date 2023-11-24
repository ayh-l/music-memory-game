package ui;

import model.Round;
import model.Sound;

import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Objects;

// Represents a sound player that allows four different sounds to be played
public class SoundPlayer implements ActionListener {
    private final File soundZero = new File("./data/trumpet.wav");
    private final File soundOne = new File("./data/sax.wav");
    private final File soundTwo = new File("./data/trombone.wav");
    private final File soundThree = new File("./data/violin.wav");

    public SoundPlayer() {

    }

    // (CODE FROM: https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java)
    // EFFECTS: plays the given .wav sound file
    public void playSound(File f) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    private void playSound(Sound s) {
        int label = s.getLabel();
        if (label == 0) {
            playSound(soundZero);
        }
        if (label == 1) {
            playSound(soundOne);
        }
        if (label == 2) {
            playSound(soundTwo);
        }
        if (label == 3) {
            playSound(soundThree);
        }
    }

    // EFFECTS: plays the sounds corresponding to given round's sound list
    public void playSoundList(Round r) throws InterruptedException {
        List<Sound> soundList = r.getSoundList();
        for (Sound s : soundList) {
            playSound(s);
            Thread.sleep(500);
        }
    }

    @Override
    // EFFECTS: according to the event e, plays the corresponding sound
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (Objects.equals(command, "sound 0")) {
            playSound(soundZero);
        }
        if (Objects.equals(command, "sound 1")) {
            playSound(soundOne);
        }
        if (Objects.equals(command, "sound 2")) {
            playSound(soundTwo);
        }
        if (Objects.equals(command, "sound 3")) {
            playSound(soundThree);
        }

    }
}