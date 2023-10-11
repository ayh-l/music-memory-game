package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Sound.*;
import static org.junit.jupiter.api.Assertions.*;

public class RoundTest {
    private Round testRound;
    private RoundHistory testRoundHistory;

    @BeforeEach
    public void setup() {
        testRound = new Round();
        testRoundHistory = new RoundHistory();
    }

    // (TODO: EDIT) NOTE: code is based on edX Abstraction 2: Testing a Data Abstraction / Extra Practice (Coin example)
    @Test
    public void testConstructor() {
        assertEquals(testRound.getSoundList().size(), 0);

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
    public void testNextSoundWrongSound() {
        assertFalse(testRoundHistory.getRounds().contains(testRound));

        Sound notNextSound;
        if (testRound.getNextCorrectSound() == SOUND_0) {
            notNextSound = Sound.SOUND_1;
        } else {
            notNextSound = SOUND_0;
        }

        assertFalse(testRound.nextSound(notNextSound, testRoundHistory));
        assertTrue(testRoundHistory.getRounds().contains(testRound));
    }

    //(TODO: EDIT) NOTE: code is based on edX Abstraction 2: Testing a Data Abstraction / Extra Practice (Coin example)
    @Test
    public void testNextSoundCorrectSound() {
        assertFalse(testRoundHistory.getRounds().contains(testRound));
        Sound nextSound;
        Sound previousSound;
        int timesSwitched = 0;

        for (int count = 1; count <= 100; count++) {
            previousSound = testRound.getNextCorrectSound();
            nextSound = testRound.getNextCorrectSound();
            assertTrue(testRound.nextSound(nextSound, testRoundHistory));
            nextSound = testRound.getNextCorrectSound();

            if (nextSound != previousSound) {
                timesSwitched++;
            }
            assertTrue(timesSwitched > 0);
        }

        assertFalse(testRoundHistory.getRounds().contains(testRound));
    }

    @Test
    public void testFindSound() {
        assertEquals(testRound.findSound(0), SOUND_0);
        assertEquals(testRound.findSound(1), SOUND_1);
        assertEquals(testRound.findSound(2), SOUND_2);
        assertEquals(testRound.findSound(3), SOUND_3);
    }

}
