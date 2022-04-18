package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MostEnergyQuestionTest {
    private MostEnergyQuestion q1;
    private MostEnergyQuestion q2;
    private MostEnergyQuestion q3;
    private MostEnergyQuestion q4;
    private MostEnergyQuestion q5;

    private Activity act1;
    private Activity act2;
    private Activity act3;
    private Activity act4;
    private Activity act5;
    private Activity act6;
    @BeforeEach
    void setUp() {

        act1 = new Activity(1L, "Cook one egg", 1000);
        act2 = new Activity(1L, "Cook one egg", 1000);
        act3 = new Activity(2L, "Cook two eggs", 2000);
        act4 = new Activity(2L, "Cook five eggs", 5000);
        act5 = new Activity(3L, "Charge phone", 2000);
        act6 = new Activity(4L, "Run the washing machine", 3000);

        q1 = new MostEnergyQuestion(act1, 2000, 40, new ArrayList<Activity>(
                Arrays.asList(act2, act3)
        ));
        q2 = new MostEnergyQuestion(act2, 2000, 40, new ArrayList<Activity>(
                Arrays.asList(act1, act3)
        ));
        q3 = new MostEnergyQuestion(act6, 2000, 40, new ArrayList<Activity>(
                Arrays.asList(act4, act5)
        ));
        q4 = new MostEnergyQuestion(act3, 2000, 40, new ArrayList<Activity>(
                Arrays.asList(act5, act6)
        ));

        q5 = new MostEnergyQuestion(act4, 2000, 40, new ArrayList<Activity>(
                Arrays.asList(act2, act3)
        ));
    }

    @Test
    void checkConstructor() {
        assertNotNull(q1);
    }

    @Test
    void testEquals() {
        assertTrue(q1.equals(q2));
    }

    @Test
    void testEqualHashCode() {
        assertTrue(q1.hashCode() == q2.hashCode());
    }

    @Test
    void testNotEquals() {
        assertNotEquals(q2, q4);
    }

    @Test
    void checkValidity() {
        assertFalse(q3.checkValidity());
        assertTrue(q5.checkValidity());
    }

    @Test
    void getOtherActivities(){
        ArrayList<Activity> test = new ArrayList<Activity>(Arrays.asList(act2, act3));
        assertEquals(q1.getOtherActivities(), test);
    }

    @Test
    void setOtherActivities(){
        ArrayList<Activity> test = new ArrayList<Activity>(Arrays.asList(act4, act5));
        q1.setOtherActivities(test);
        assertEquals(q1.getOtherActivities(), test);
    }

    @Test
    void testSetCorrectAnswer(){
        q1.setCorrectAnswer(act1);
        assertEquals(act1, q1.getCorrectAnswer());
    }

    @Test
    void testGetCorrectAnswer(){
        q2.setCorrectAnswer(act4);
        assertEquals(act4, q2.getCorrectAnswer());
    }

    @Test
    void constructorTest(){
        MostEnergyQuestion qq = new MostEnergyQuestion();
        assertNotNull(qq);
    }

    @Test
    void toStringTest(){
        String res = "MostEnergyQuestion{otherActivities=[Activity{id='1', " +
                "image_path='change/this', title='Cook one egg', consumption_in_wh=1000, " +
                "source='change/this'}, Activity{id='2', image_path='change/this', title='Cook two eggs', " +
                "consumption_in_wh=2000, source='change/this'}], correctAnswer=null}";
        assertEquals(res, q1.toString());
    }
}