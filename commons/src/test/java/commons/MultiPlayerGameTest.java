package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class MultiPlayerGameTest {

    Player player1 = new Player("som", 320);
    Player player2 = new Player("ansh", 220);
    Player player3 = new Player("alex", 420);
    Player player4 = new Player("max", 340);
    Player player5 = new Player("rafael", 330);

    private GuessQuestion q1;
    private GuessQuestion q2;

    private InsteadOfQuestion q3;
    private InsteadOfQuestion q4;

    private MultipleChoiceQuestion q5;
    private MultipleChoiceQuestion q6;

    private MostEnergyQuestion q7;
    private MostEnergyQuestion q8;

    MultiPlayerGame mpg;
    MultiPlayerGame mpg2;
    MultiPlayerGame mpg3;

    @BeforeEach
    void setUp() {

        q1 = new GuessQuestion(new Activity(1L, "Cook one egg", 1000), 1000,
                "EASY", 30);

        q2 = new GuessQuestion(new Activity(2L, "Cook two eggs", 2000), 1000,
                60);

        q3 = new InsteadOfQuestion(new Activity(4L, "Run the washing machine", 3000),
                2000, 40, new ArrayList<Activity>
                (Arrays.asList(new Activity(2L, "Cook five eggs", 5000),
                        new Activity(3L, "Charge phone", 2000))));

        q4 = new InsteadOfQuestion(new Activity(2L, "Cook two eggs", 2000), 2000,
                40, new ArrayList<Activity>
                (Arrays.asList(new Activity(3L, "Charge phone", 2000),
                        new Activity(4L, "Run the washing machine", 3000))));


        q5 = new MultipleChoiceQuestion(new Activity(2L, "Cook two eggs", 2000), 2000,
                30);

        q6 = new MultipleChoiceQuestion(new Activity(1L, "Cook one egg", 1000),
                3000, "HARD", 30);

        q7 = new MostEnergyQuestion(new Activity(2L, "Cook five eggs", 5000),
                2000, 40, new ArrayList<Activity>(
                Arrays.asList(new Activity(1L, "Cook one egg", 1000),
                        new Activity(2L, "Cook two eggs", 2000))));

        q8 = new MostEnergyQuestion(new Activity(1L, "Cook one egg", 1000),
                2000, 40, new ArrayList<Activity>(
                Arrays.asList(new Activity(1L, "Cook one egg", 1000),
                        new Activity(2L, "Cook two eggs", 2000))
        ));

        AdditionalPointsJoker additionalPointsJoker1 = new AdditionalPointsJoker("Additional",
                "Adds 10 additional points if you answer correctly",
                false, player1, q4);

        mpg = new MultiPlayerGame(
                new ArrayList<Question>(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8)),
                new ArrayList<JokerCard>(Arrays.asList(additionalPointsJoker1)),
                new ArrayList<Player>(Arrays.asList(player1, player2, player3, player4, player5)));

        mpg2 = new MultiPlayerGame(
                new ArrayList<Question>(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8)),
                new ArrayList<JokerCard>(Arrays.asList(additionalPointsJoker1)),
                new ArrayList<Player>(Arrays.asList(player1, player2, player3, player4, player5)));

        mpg3 = new MultiPlayerGame(
                new ArrayList<Question>(Arrays.asList(q1, q2, q3, q4, q5, q6, q7)),
                new ArrayList<JokerCard>(Arrays.asList(additionalPointsJoker1)),
                new ArrayList<Player>(Arrays.asList(player1, player2, player3, player4, player5)));
    }

    @Test
    void checkConstructor() {
        assertNotNull(mpg);
    }

    @Test
    void testEquals() {
        assertTrue(mpg.equals(mpg2));
    }

    @Test
    void testEqualHashCode() {
        assertTrue(mpg.hashCode() == mpg2.hashCode());
    }

    @Test
    void testNotEquals() {
        assertNotEquals(mpg, mpg3);
    }

    @Test
    void setPlayers() {
        ArrayList<Player> newList = new ArrayList<>(Arrays.asList(player2, player4));
        mpg.setPlayers(newList);
        assertEquals(mpg.getPlayers(), newList);
    }

    @Test
    void setLeaderboard() {
        HashMap<Player, Integer> leaderboard = new HashMap<>();
        leaderboard.put(player1, 230);
        leaderboard.put(player5, 71);
        mpg.setLeaderboard(leaderboard);
        assertEquals(mpg.getLeaderboard(), leaderboard);
    }


    @Test
    void getLeaderboard() {
        HashMap<Player, Integer> leaderboard = new HashMap<>();
        leaderboard.put(player1, 100);
        leaderboard.put(player5, 199);
        mpg.setLeaderboard(leaderboard);
        assertEquals(mpg.getLeaderboard(), leaderboard);
    }

    @Test
    void testToString() {
        String test = mpg.toString();
        assertEquals("MultiPlayerGame{players=[Player{id=null, " +
                "username='som', currentScore=320}, Player{id=null, username='ansh', currentScore=220}," +
                " Player{id=null, username='alex', currentScore=420}, Player{id=null, " +
                "username='max', currentScore=340}, Player{id=null, username='rafael', " +
                "currentScore=330}], leaderboard={}}",
                mpg.toString());
    }
}
