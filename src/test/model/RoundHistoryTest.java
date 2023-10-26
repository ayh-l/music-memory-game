package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoundHistoryTest {
    private RoundHistory testRoundHistory;
    private Round round1;
    private Round round2;
    private List<Sound> testSoundList1;
    private List<Sound> testSoundList2;

    @BeforeEach
    public void setup() {
        testRoundHistory = new RoundHistory();
        round1 = new Round();
        round2 = new Round();
        testSoundList1 = new ArrayList<>();
        testSoundList2 = new ArrayList<>();
    }

    @Test
    public void testConstructor() {
        assertEquals(testRoundHistory.getCompletedRounds().size(), 0);
        assertEquals(testRoundHistory.getHighScore(), 0);
        assertNull(testRoundHistory.getCurrentRound());
    }

    @Test
    public void testFinishRoundNoNewHighScore() {
        testSoundList1.add(Sound.SOUND_1);
        testSoundList1.add(Sound.SOUND_0);
        testSoundList2.add(Sound.SOUND_2);
        testSoundList2.add(Sound.SOUND_2);
        testSoundList2.add(Sound.SOUND_2);
        round1.setSoundList(testSoundList1);
        round2.setSoundList(testSoundList2);

        assertEquals(testRoundHistory.getCompletedRounds().size(), 0);
        assertEquals(testRoundHistory.getNumRoundsPlayed(), 0);
        testRoundHistory.setHighScore(10);
        assertEquals(testRoundHistory.getHighScore(), 10);

        testRoundHistory.setCurrentRound(round1);
        testRoundHistory.finishRound();
        testRoundHistory.setCurrentRound(round2);
        testRoundHistory.finishRound();

        assertEquals(testRoundHistory.getCompletedRounds().size(), 2);
        assertEquals(testRoundHistory.getHighScore(), 10);
        assertEquals(testRoundHistory.getNumRoundsPlayed(), 2);

    }

    @Test
    public void testFinishRoundNewHighScore() {
        testSoundList1.add(Sound.SOUND_1);
        testSoundList1.add(Sound.SOUND_0);
        testSoundList2.add(Sound.SOUND_2);
        testSoundList2.add(Sound.SOUND_2);
        testSoundList2.add(Sound.SOUND_2);
        round1.setSoundList(testSoundList1);
        round2.setSoundList(testSoundList2);

        assertEquals(testRoundHistory.getCompletedRounds().size(), 0);
        assertEquals(testRoundHistory.getNumRoundsPlayed(), 0);
        testRoundHistory.setHighScore(2);
        assertEquals(testRoundHistory.getHighScore(), 2);

        testRoundHistory.setCurrentRound(round1);
        testRoundHistory.finishRound();
        testRoundHistory.setCurrentRound(round2);
        testRoundHistory.finishRound();

        assertEquals(testRoundHistory.getCompletedRounds().size(), 2);
        assertEquals(testRoundHistory.getHighScore(), 3);
        assertEquals(testRoundHistory.getNumRoundsPlayed(), 2);
    }

    @Test
    public void testFinishRound() {
        testRoundHistory.setCurrentRound(round1);

        assertEquals(testRoundHistory.getCompletedRounds().size(), 0);
        assertEquals(testRoundHistory.getCurrentRound(), round1);

        testRoundHistory.finishRound();

        assertEquals(testRoundHistory.getCompletedRounds().size(), 1);
        assertEquals(testRoundHistory.getCompletedRounds().get(0), round1);
        assertNull(testRoundHistory.getCurrentRound());
    }

    @Test
    public void testStartNewRound() {
        assertNull(testRoundHistory.getCurrentRound());
        testRoundHistory.startNewRound();

        assertNotNull(testRoundHistory.getCurrentRound());
    }

    @Test
    public void testSelectCompletedRound() {
        testRoundHistory.setCurrentRound(round1);
        testRoundHistory.finishRound();
        testRoundHistory.setCurrentRound(round2);
        testRoundHistory.finishRound();

        assertEquals(testRoundHistory.selectCompletedRound(0), round1);
        assertEquals(testRoundHistory.selectCompletedRound(1), round2);
    }

    @Test
    public void testToJson() {
        testSoundList1.add(Sound.SOUND_1);
        testSoundList1.add(Sound.SOUND_0);
        testSoundList2.add(Sound.SOUND_0);
        testSoundList2.add(Sound.SOUND_2);
        testSoundList2.add(Sound.SOUND_3);
        round1.setSoundList(testSoundList1);
        round1.setNextCorrectSound(Sound.SOUND_0);
        round2.setSoundList(testSoundList2);
        round1.setNextCorrectSound(Sound.SOUND_3);

        testRoundHistory.setCurrentRound(round1);
        testRoundHistory.finishRound();
        testRoundHistory.setCurrentRound(round2);
        testRoundHistory.finishRound();

        JSONObject testJson = testRoundHistory.toJson();

        assertEquals(testJson.get("highscore"), 3);
        assertEquals(testJson.get("num rounds"), 2);

        JSONArray jsonArray = new JSONArray(testJson.get("rounds").toString());
        JSONObject jsonRound1 = jsonArray.getJSONObject(0);
        JSONObject jsonRound2 = jsonArray.getJSONObject(1);
        assertEquals(((JSONArray) jsonRound1.get("soundlist")).get(0), "1");
        assertEquals(((JSONArray) jsonRound1.get("soundlist")).get(1), "0");
        jsonRound1.get("next sound");
        assertEquals(((JSONArray) jsonRound2.get("soundlist")).get(0), "0");
        assertEquals(((JSONArray) jsonRound2.get("soundlist")).get(1), "2");
        assertEquals(((JSONArray) jsonRound2.get("soundlist")).get(2), "3");
        jsonRound2.get("next sound");

    }

}
