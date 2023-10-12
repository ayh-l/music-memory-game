package model;

import org.junit.jupiter.api.Test;

import static model.Sound.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoundTest {

    @Test
    public void testFindSound() {
        assertEquals(findSound(0), SOUND_0);
        assertEquals(findSound(1), SOUND_1);
        assertEquals(findSound(2), SOUND_2);
        assertEquals(findSound(3), SOUND_3);
    }
}
