package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MultipleChoiceQuestionTest {

    private MultipleChoiceQuestion q1;
    private MultipleChoiceQuestion q2;
    private MultipleChoiceQuestion q3;
    private MultipleChoiceQuestion q4;
    private MultipleChoiceQuestion q5;

    private Activity act1;
    private Activity act2;
    private Activity act3;



    @BeforeEach
    public void setup() {
        act1 = new Activity("00-shower",
                "00/shower.png",
                "Taking a hot shower for 6 minutes",
                4000,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
        act2 = new Activity("00-shower",
                "00/shower.png",
                "Taking a hot shower for 6 minutes",
                4000,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
        act3 = new Activity("00-smartphone",
                "00/smartphone.png",
                "Charging your smartphone at night",
                2546,
                "https://9to5mac.com/2021/09/16/iphone-13-battery-life/");

        q1 = new MultipleChoiceQuestion(act1, 1000, "EASY", 30);
        q2 = new MultipleChoiceQuestion(act2, 2000, "MEDIUM", 30);
        q3 = new MultipleChoiceQuestion(act3, 3000, "HARD", 30);
        q4 = new MultipleChoiceQuestion(act3, 3000, "HARD", 30);
        q5 = new MultipleChoiceQuestion(act3, 2000, 30);

    }

    @Test
    void checkConstructor() {
        assertNotNull(q1);
    }

    @Test
    void checkConstructorWithoutDifficulty() {
        assertNotNull(q5);
    }

    @Test
    void checkIllegalArgumentConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new MultipleChoiceQuestion(act1, 2000, "TEST FAILURE", 30));
    }

    @Test
    void testNotEquals() {
        assertNotEquals(q2, q5);
    }

    @Test
    void getDifficulty() {
        assertEquals(q5.getDifficulty(),"EASY");
    }

    @Test
    void checkAnswerRangeEasy() {
        ArrayList<Long> options = q1.getOptions();

        Set<Long> s = new HashSet<>(options);

        assertEquals(s.size(), 3);

    }

    @Test
    void checkAnswerRangeMedium() {
        ArrayList<Long> options = q2.getOptions();

        Set<Long> s = new HashSet<>(options);

        assertEquals(s.size(), 3);
    }

    @Test
    void checkAnswerRangeHard() {
        ArrayList<Long> options = q3.getOptions();

        // for each option check whether they are greater than 1899 and less than 2101
        Set<Long> s = new HashSet<>(options);

        assertEquals(s.size(), 3);
    }

    @Test
    void getAvailablePointsTest() {
        assertEquals(2000, q2.getAvailablePoints());
    }



}