package model;

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


    // getters
    public int getLabel() {
        return this.label;
    }
}
