package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.String.valueOf;
import static model.Sound.*;

// Represents a memory game round with a sound list and a status
public class Round {
    private List<Sound> soundList;     // list of sounds accumulated during round in the order they were accumulated
    private Sound nextCorrectSound;    // newly added sound - last sound in soundList

    // EFFECTS: constructs a new round with a randomly selected next correct sound and a list of sounds containing that
    //          next correct sound.
    public Round() {
        this.soundList = new ArrayList<>();
        this.nextCorrectSound = null;
        this.setNextCorrectSound();
    }

    // REQUIRES: index is < size of this.getCompleteSoundList()
    // EFFECTS: produces true if guess matches Sound at index position in this round's
    public boolean guessSound(int index, Sound guess) {
        return this.soundList.get(index) == guess;
    }

    // (REFERENCED: JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
    // EFFECT: returns this round as a JSON object
    public JSONObject roundToJson() {
        JSONObject json = new JSONObject();
        JSONArray soundlist = new JSONArray();

        for (Sound s : this.getSoundList()) {
            soundlist.put(String.valueOf(s.getLabel()));
        }
        json.put("soundlist", soundlist);
        json.put("next sound", valueOf(this.nextCorrectSound.getLabel()));

        return json;
    }

    // (REFERENCED: https://www.educative.io/answers/how-to-generate-random-numbers-in-java)
    // REQUIRES: must only be called when creating a round or after a correct recitation of sound list
    // MODIFIES: this
    // EFFECTS: sets this.getNextCorrectSound() to a random Sound and adds it to the end of this round's sound list
    public void setNextCorrectSound() {
        Random random = new Random();
        int nextSoundLabel = random.nextInt(Sound.NUMBER_OF_SOUNDS + 1);
        Sound nextSound = findSound(nextSoundLabel);
        this.nextCorrectSound = nextSound;
        this.soundList.add(nextSound);
    }

    // setters
    public void setNextCorrectSound(Sound s) {
        this.nextCorrectSound = s;
    }

    // getters
    public List<Sound> getSoundList() {
        return soundList;
    }

    public Sound getNextCorrectSound() {
        return nextCorrectSound;
    }

    public void setSoundList(List<Sound> sl) {
        this.soundList = sl;
    }

}