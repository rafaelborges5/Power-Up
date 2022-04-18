package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game g;
    Game g1;
    Question q;
    Player p;
    AdditionalPointsJoker j;

    @BeforeEach
    void setUp() {
        p = new Player("noname", 15);
        Activity activity = new Activity("00-shower",
                "00/shower.png",
                "Taking a hot shower for 6 minutes",
                4000,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
        q = new MultipleChoiceQuestion(activity, 10,"EASY", 20);
        j = new AdditionalPointsJoker("Additional",
                "Adds 10 additional points if you answer correctly",
                false, p, q);
        g = new SinglePlayerGame(new ArrayList<>(List.of(q)), new ArrayList<>(List.of(j)), p);
        g1 = new SinglePlayerGame(new ArrayList<>(List.of(q)), new ArrayList<>(List.of(j)), p);
    }

    @Test
    void getQuestions() {
        assertEquals(new ArrayList<>(List.of(q)), g.getQuestions());
    }

    @Test
    void getJokerCards() {
        assertEquals(new ArrayList<>(List.of(j)), g.getJokerCards());
    }

    @Test
    void setCurrentQuestionNumber() {
        g.setCurrentQuestionNumber(0);
        assertEquals(0, g.getCurrentQuestionNumber());
    }

    @Test
    void getCurrentQuestionNumber() {
        assertEquals(1, g.getCurrentQuestionNumber());
    }

    @Test
    void testEquals() {
        assertNotEquals(g, new SinglePlayerGame());
        assertEquals(g, g1);
    }

    @Test
    void testHashCode() {
        assertEquals(g.hashCode(), g1.hashCode());
    }

    @Test
    void testToString() {
        System.out.println(g.toString());
    }

    @Test
    void setGameOver() {
        g.setGameOver(true);
        assertTrue(g.isGameOver());
    }

    @Test
    void isGameOver() {
        assertFalse(g.isGameOver());
    }
}