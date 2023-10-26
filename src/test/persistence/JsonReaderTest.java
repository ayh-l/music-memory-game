package persistence;

import model.RoundHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private JsonReader testReader;

    @Test
    public void testReaderNonexistentFile() {
        JsonReader reader = new JsonReader("./data/doesNotExist");
        try {
            reader.read();
            fail("IOException expected but was not thrown.");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReadEmptyRoundHistory() {
        testReader = new JsonReader("./data/test/testReadEmptyRoundHistory.json");

        RoundHistory readRoundHistory;
        try {
            readRoundHistory = testReader.read();
        } catch (IOException e) {
            readRoundHistory = null;
            fail("IOException thrown but not expected");
        }

        assertEquals(readRoundHistory.getCompletedRounds().size(), 0);
        assertNull(readRoundHistory.getCurrentRound());
        assertEquals(readRoundHistory.getNumRoundsPlayed(), 0);
        assertEquals(readRoundHistory.getHighScore(), 0);
    }

    @Test
    public void testReadNonemptyRoundHistory() {
        testReader = new JsonReader("./data/test/testNonemptyRoundHistory.json");
        RoundHistory readRoundHistory;
        try {
            readRoundHistory = testReader.read();
        } catch (IOException e) {
            fail("IOException was thrown but not expected");
            readRoundHistory = null;
        }

        assertEquals(readRoundHistory.getHighScore(), 3);
        assertEquals(readRoundHistory.getCompletedRounds().size(), 2);
        assertNull(readRoundHistory.getCurrentRound());
        assertEquals(readRoundHistory.getNumRoundsPlayed(), 2);
    }
}
