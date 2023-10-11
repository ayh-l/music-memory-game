package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.Sound.*;

// Represents a memory game round with a sound list and a status
public class Round {
    private final List<Sound> soundList;  // list of sounds accumulated during round
    private Sound nextCorrectSound;       // next sound that will be added to soundList (if user recalls it correctly)

    // EFFECTS: constructs a new round with an empty list of sounds and a randomly selected next correct sound
    public Round() {
        this.soundList = new ArrayList<>();
        this.nextCorrectSound = null;
        this.setNextCorrectSound();
    }

    // REQUIRES: current round is not completed
    // MODIFIES: this, rh
    // EFFECTS: if s matches the next correct sound, adds s to current round's sound list, returns true, and sets a new
    //          next correct sound. Otherwise, adds this round to rh and returns false.
    public boolean nextSound(Sound s, RoundHistory rh) {
        if (s == this.nextCorrectSound) {
            this.addSound(s);
            this.setNextCorrectSound();
            return true;
        }
        rh.getRounds().add(this);
        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds s to current round's sound list
    public void addSound(Sound s) {
        this.soundList.add(s);
    }

    // REQUIRES: must follow a call to this.nextSound() that returned true (and this.setNextCorrectSound() has not yet
    //           been called after that call to this.nextSound())
    // MODIFIES: this
    // EFFECTS: sets this.getNextCorrectSound() to a random Sound
    private void setNextCorrectSound() {
        Random random = new Random();
        int nextSoundLabel = random.nextInt(Sound.NUMBER_OF_SOUNDS + 1);
        this.nextCorrectSound = findSound(nextSoundLabel);
    }

    // REQUIRES: int i is in [0, NUMBER_OF_SOUNDS]
    // EFFECTS: produces the Sound that has the given integer as its label
    public Sound findSound(int i) {
        Sound findingSound = null;
        for (Sound s : getSoundTypes()) {
            if (s.getLabel() == i) {
                findingSound = s;
            }
        }
        return findingSound;
    }

    // EFFECTS: returns a list of all the possible sound types
    private List<Sound> getSoundTypes() {
        List<Sound> sounds = new ArrayList<>();
        sounds.add(SOUND_0);
        sounds.add(SOUND_1);
        sounds.add(SOUND_2);
        sounds.add(SOUND_3);
        return sounds;
    }


    // getters
    public List<Sound> getSoundList() {
        return soundList;
    }

    public Sound getNextCorrectSound() {
        return nextCorrectSound;
    }

}