package model;

// (REFERENCED: https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)

import java.util.ArrayList;
import java.util.List;

// Represents a single sound occurrence in a game (may be one of four different sounds)
public enum Sound {
    SOUND_0(0),
    SOUND_1(1),
    SOUND_2(2),
    SOUND_3(3);

    private final int label;                        // the unique number corresponding to the sound
    public static final int NUMBER_OF_SOUNDS = 3;   // the total number of sounds (in 0 based indexing)

    // EFFECTS: constructs a sound with label set as the given integer
    Sound(int i) {
        this.label = i;
    }

    // REQUIRES: int i is in [0, NUMBER_OF_SOUNDS]
    // EFFECTS: produces the Sound that has the given integer as its label
    public static Sound findSound(int i) {
        Sound findingSound = null;
        for (Sound s : getSoundTypes()) {
            if (s.getLabel() == i) {
                findingSound = s;
            }
        }
        return findingSound;
    }

    // EFFECTS: returns a list of all the possible sound types
    private static List<Sound> getSoundTypes() {
        List<Sound> sounds = new ArrayList<>();
        sounds.add(SOUND_0);
        sounds.add(SOUND_1);
        sounds.add(SOUND_2);
        sounds.add(SOUND_3);
        return sounds;
    }

    // getters
    public int getLabel() {
        return this.label;
    }
}
