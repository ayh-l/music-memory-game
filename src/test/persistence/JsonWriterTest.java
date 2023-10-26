package persistence;

import model.Round;
import model.RoundHistory;
import model.Sound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// (REFERERENCED: JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)

public class JsonWriterTest {
    private JsonWriter testWriter;
    private JsonReader testReader;
    private RoundHistory testRoundHistory;

    @BeforeEach
    public void setup() {
        testRoundHistory = new RoundHistory();
    }

    @Test
    public void testOpenThrowsException() {
        testWriter = new JsonWriter("json:writer/test.json");
        try {
            testWriter.open();
            fail("FileNotFoundException expected but not thrown");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testWriteEmptyRoundHistory() {
        testWriter = new JsonWriter("./data/test/testWriteEmptyRoundHistory.json");
        try {
            testWriter.open();
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException thrown but was not expected");
        }
        testWriter.write(testRoundHistory);
        testWriter.close();

        testReader = new JsonReader("./data/test/testWriteEmptyRoundHistory.json");
        RoundHistory saved = null;
        try {
            saved = testReader.read();
        } catch (IOException e) {
            fail("IOException thrown");
        }
        assertEquals(saved.getCompletedRounds().size(), 0);
        assertNull(saved.getCurrentRound());
        assertEquals(saved.getNumRoundsPlayed(), 0);
        assertEquals(saved.getHighScore(), 0);
    }

    @Test
    public void testWriteNonemptyRoundHistory() {
        Round testRound1 = new Round();
        Round testRound2 = new Round();
        Round testRound3 = new Round();
        List<Sound> testSoundlist1 = new ArrayList<>();
        List<Sound> testSoundlist2 = new ArrayList<>();
        List<Sound> testSoundlist3 = new ArrayList<>();
        testSoundlist1.add(Sound.SOUND_0);
        testSoundlist1.add(Sound.SOUND_2);
        testSoundlist1.add(Sound.SOUND_3);
        testSoundlist2.add(Sound.SOUND_3);
        testSoundlist3.add(Sound.SOUND_0);
        testRound1.setSoundList(testSoundlist1);
        testRound2.setSoundList(testSoundlist2);
        testRound3.setSoundList(testSoundlist3);
        testRound1.setNextCorrectSound(Sound.SOUND_1);
        testRound2.setNextCorrectSound(Sound.SOUND_0);
        testRound3.setNextCorrectSound(Sound.SOUND_3);
        testRoundHistory.setCurrentRound(testRound1);
        testRoundHistory.finishRound();
        testRoundHistory.setCurrentRound(testRound2);
        testRoundHistory.finishRound();
        testRoundHistory.setCurrentRound(testRound3);

        testWriter = new JsonWriter("./data/test/testWriteNonemptyRoundHistory.json");
        try {
            testWriter.open();
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException thrown but was not expected");
        }
        testWriter.write(testRoundHistory);
        testWriter.close();

        testReader = new JsonReader("./data/test/testWriteNonemptyRoundHistory.json");
        RoundHistory saved = null;
        try {
            saved = testReader.read();
        } catch (IOException e) {
            fail("IOException thrown");
        }
        assertEquals(saved.getNumRoundsPlayed(), 2);
        assertEquals(saved.getHighScore(), 3);

        assertEquals(saved.getCompletedRounds().size(), 2);
        List<Round> crs = saved.getCompletedRounds();
        Round first = crs.get(0);
        assertEquals(first.getSoundList().size(), 3);
        assertEquals(first.getSoundList().get(0), Sound.SOUND_0);
        assertEquals(first.getSoundList().get(1), Sound.SOUND_2);
        assertEquals(first.getSoundList().get(2), Sound.SOUND_3);
        assertEquals(first.getNextCorrectSound(), Sound.SOUND_1);
        Round second = crs.get(1);
        assertEquals(second.getSoundList().size(), 1);
        assertEquals(second.getSoundList().get(0), Sound.SOUND_3);
        assertEquals(second.getNextCorrectSound(), Sound.SOUND_0);
    }

}
