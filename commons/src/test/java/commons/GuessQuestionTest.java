package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GuessQuestionTest {

    private GuessQuestion q1;
    private GuessQuestion q2;
    private GuessQuestion q3;
    private GuessQuestion q4;
    private GuessQuestion q5;

    private Activity act1;
    private Activity act2;
    private Activity act3;

    @BeforeEach
    void setUp() {
        act1 = new Activity(1L, "Cook one egg", 1000);
        act2 = new Activity(1L, "Cook one egg", 1000);
        act3 = new Activity(2L, "Cook two eggs", 2000);

        q1 = new GuessQuestion(act1, 1000, "EASY", 30);
        q2 = new GuessQuestion(act2, 1000, 60);
        q3 = new GuessQuestion(act1, 1000, 30);
        q4 = new GuessQuestion(act1, 1000, 30);
    }

    @Test
    void checkConstructor() {
        assertNotNull(q1);
        GuessQuestion g = new GuessQuestion();
        assertNotNull(g);
    }

    @Test
    void checkConstructorWithoutDifficulty() {
        assertNotNull(q4);
    }

    @Test
    void calculateCloseness() {
        assertEquals(0, q1.calculateCloseness(2100));
        assertEquals(0, q1.calculateCloseness(0));
        assertEquals(0, q1.calculateCloseness(1500));
        assertEquals(0, q1.calculateCloseness(400));
        assertEquals(0, q1.calculateCloseness(1600));
        assertEquals(0.8, q1.calculateCloseness(1100));
    }

    @Test
    void testCalculatePoints() {
        assertEquals(1000, q1.calculatePoints(1000));
    }

    @Test
    void testEquals() {
        assertTrue(q3.equals(q4));
    }

    @Test
    void testEqualHashCode() {
        assertTrue(q3.hashCode() == q4.hashCode());
    }

    @Test
    void testNotEquals() {
        assertNotEquals(q2, q4);
    }


}