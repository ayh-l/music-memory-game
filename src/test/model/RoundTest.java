package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Sound.*;
import static org.junit.jupiter.api.Assertions.*;

public class RoundTest {
    private Round testRound;
    private RoundHistory testRoundHistory;
    private List<Sound> testSoundList;

    @BeforeEach
    public void setup() {
        testRound = new Round();
        testRoundHistory = new RoundHistory();
        testRoundHistory.setCurrentRound(testRound);
        testSoundList = new ArrayList<>();
    }

    // (REFERENCED: edX Abstraction 2: Testing a Data Abstraction / Extra Practice (Coin example))
    @Test
    public void testConstructor() {
        assertEquals(testRound.getSoundList().size(), 1);

        int timesSwitched = 0;

        for (int count = 1; count <= 100; count++) {
            Sound previousSound = testRound.getNextCorrectSound();
            testRound = new Round();
            if (testRound.getNextCorrectSound() != previousSound) {
                timesSwitched++;
            }
        }
        assertTrue(timesSwitched > 0);
    }

    @Test
    public void testGuessSound() {
        assertEquals(testRound.getSoundList().size(), 1);
        testSoundList.add(testRound.getSoundList().get(0));
        testSoundList.add(SOUND_2);
        testSoundList.add(SOUND_3);
        testRound.setSoundList(testSoundList);

        assertFalse(testRound.guessSound(2, SOUND_2));
        assertTrue(testRound.guessSound(0, testRound.getNextCorrectSound()));
        assertTrue(testRound.guessSound(1, SOUND_2));
    }

    // (REFERENCED: edX Abstraction 2: Testing a Data Abstraction / Extra Practice (Coin example))
    @Test
    public void testSetNextCorrectSound() {
        Sound nextSound;
        Sound previousSound;
        int timesSwitched = 0;
        assertEquals(testRound.getSoundList().size(), 1);

        for (int count = 1; count <= 100; count++) {
            previousSound = testRound.getNextCorrectSound();
            testRound.setNextCorrectSound();
            nextSound = testRound.getNextCorrectSound();
            assertEquals(testRound.getSoundList().get(testRound.getSoundList().size() - 1),
                    testRound.getNextCorrectSound());

            if (nextSound != previousSound) {
                timesSwitched++;
            }
        }
        assertTrue(timesSwitched > 0);
        assertEquals(testRound.getSoundList().size(), 101);
        assertFalse(testRoundHistory.getCompletedRounds().contains(testRound));
        assertEquals(testRoundHistory.getCurrentRound(), testRound);
    }

    @Test
    public void testRoundToJson() {
        testSoundList.add(SOUND_1);
        testSoundList.add(SOUND_2);
        testSoundList.add(SOUND_3);
        testRound.setNextCorrectSound(SOUND_3);
        testRound.setSoundList(testSoundList);
        JSONObject testJson = testRound.roundToJson();

        assertEquals(((JSONArray) testJson.get("soundlist")).get(0), "1");
        assertEquals(((JSONArray) testJson.get("soundlist")).get(1), "2");
        assertEquals(((JSONArray) testJson.get("soundlist")).get(2), "3");
        assertEquals(testJson.get("next sound"), "3");
    }

}
