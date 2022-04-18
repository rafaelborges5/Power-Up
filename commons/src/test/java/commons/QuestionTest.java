package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private Question q1;
    private Question q2;
    private Question q3;
    private Question q4;

    private Activity act1;
    private Activity act2;
    private Activity act3;
    private Activity act4;

    @BeforeEach
    void setUp() {
        act1 = new Activity(1L, "Cook one egg", 1000);
        act2 = new Activity(2L, "Cook two egg", 2000);
        act3 = new Activity(3L, "Cook three eggs", 3000);
        act4 = new Activity(10L, "Take a shower", 500);

        q1 = new GuessQuestion(act1, 1000, "EASY", 30);
        q2 = new MultipleChoiceQuestion(act2, 1000, 60);
        ArrayList<Activity> options = new ArrayList<>();
        options.add(act2);
        options.add(act3);
        options.add(act4);
        q3 = new InsteadOfQuestion(act1, 100, 25, options);
        q4 = new GuessQuestion(act1, 1000, 30);

    }

    @Test
    void getActivity() {
        assertEquals(act1, q1.getActivity());
    }

    @Test
    void getActivity2() {
        assertEquals(act1, q3.getActivity());
    }

    @Test
    void getActivity3() {
        assertEquals(act2, q2.getActivity());
    }

    @Test
    void setActivity() {
        q4.setActivity(act4);
        assertEquals(act4, q4.getActivity());
    }

    @Test
    void getAvailablePoints() {
        assertEquals(100, q3.getAvailablePoints());
    }

    @Test
    void getAvailablePoints2() {
        assertEquals(1000, q4.getAvailablePoints());
    }

    @Test
    void setAvailablePoints() {
        q2.setAvailablePoints(50);
        assertEquals(50, q2.getAvailablePoints());
    }

    @Test
    void getDifficulty() {
        assertEquals("EASY", q1.getDifficulty());
    }

    @Test
    void setDifficulty() {
        q2.setDifficulty("HARD");
        assertEquals("HARD", q2.getDifficulty());
    }

    @Test
    void getAllowedTime() {
        assertEquals(60, q2.getAllowedTime());
    }

    @Test
    void setAllowedTime() {
        q3.setAllowedTime(42);
        assertEquals(42, q3.getAllowedTime());
    }

    @Test
    void testEquals() {
        assertFalse(q1.equals(q2));
    }

    @Test
    void testEquals2() {
        GuessQuestion q5 = new GuessQuestion(act1, 1000, 30);
        assertTrue(q4.equals(q5));
    }

    @Test
    void testEquals3() {
        assertFalse(q1.equals(act1));
    }

    @Test
    void testEquals4() {
        assertTrue(q1.equals(q1));
    }

    @Test
    void testHashCode() {
        GuessQuestion q5 = new GuessQuestion(act1, 1000, 30);
        assertEquals(q5.hashCode(), q4.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Question{activity=Activity{id='2', image_path='change/this', " +
                "title='Cook two egg', consumption_in_wh=2000, source='change/this'}, " +
                "availablePoints=1000, difficulty='EASY', allowedTime=60}";
        assertEquals(expected, q2.toString());
    }
}