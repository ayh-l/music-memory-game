package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundHistoryTest {
    private RoundHistory testRoundHistory;
    private Round round1;
    private Round round2;

    @BeforeEach
    public void setup() {
        testRoundHistory = new RoundHistory();
        round1 = new Round();
        round2 = new Round();
    }

    @Test
    public void testConstructor() {
        assertEquals(testRoundHistory.getRounds().size(), 0);
        assertEquals(testRoundHistory.getHighScore(), 0);
    }

    //TODO: I made addSound public in Round even though it's a helper - is this okay?
    @Test
    public void testAddRoundNoNewHighScore() {
        round1.addSound(Sound.SOUND_1);
        round1.addSound(Sound.SOUND_0);
        round2.addSound(Sound.SOUND_2);
        round2.addSound(Sound.SOUND_2);
        round2.addSound(Sound.SOUND_2);

        assertEquals(testRoundHistory.getRounds().size(), 0);
        testRoundHistory.setHighScore(10);
        assertEquals(testRoundHistory.getHighScore(), 10);

        testRoundHistory.addRound(round1);
        testRoundHistory.addRound(round2);

        assertEquals(testRoundHistory.getRounds().size(), 2);
        assertEquals(testRoundHistory.getHighScore(), 10);

    }

    @Test
    public void testAddRoundNewHighScore() {
        round1.addSound(Sound.SOUND_1);
        round1.addSound(Sound.SOUND_0);
        round2.addSound(Sound.SOUND_2);
        round2.addSound(Sound.SOUND_2);
        round2.addSound(Sound.SOUND_2);

        assertEquals(testRoundHistory.getRounds().size(), 0);
        testRoundHistory.setHighScore(2);
        assertEquals(testRoundHistory.getHighScore(), 2);

        testRoundHistory.addRound(round1);
        testRoundHistory.addRound(round2);

        assertEquals(testRoundHistory.getRounds().size(), 2);
        assertEquals(testRoundHistory.getHighScore(), 3);
    }

}
