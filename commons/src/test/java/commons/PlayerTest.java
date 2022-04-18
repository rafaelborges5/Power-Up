package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;



class PlayerTest {
    Player player1;
    Player player2;
    Player player3;
    Player player1same;
    @BeforeEach
    public void setup() {
       player1 = new Player(1L,"rafael1234",1256);
        player2 = new Player(2L,"rafael1234",1256);
        player3 = new Player(3L,"alex1234",1300);
        player1same = new Player(1L,"rafael1234",1256);
    }
    @Test
    public void checkConstructor() {
        assertNotNull(player1);
    }
    @Test
    public void testEquals() {

        assertTrue(player1.equals(player2));
    }
    @Test
    public void testEqualHashCode() {
        assertTrue(player1.hashCode() == player1same.hashCode());
    }
    @Test
    public void testNotEquals() {

        assertNotEquals(player1, player3);
    }

    @Test
    public void testNotEqualHashCode() {

        assertNotEquals(player1, player3);
    }

    @Test
    public void testToString() {
        assertEquals(player1.toString(), "Player{id=1, username='rafael1234', currentScore=1256}");
    }

    @Test
    void testGetJokerCards(){
        List<JokerCard> jokers = new ArrayList<>();
        jokers.add(new AdditionalPointsJoker());
        Player player4 = new Player(4L, "test", 123);
        player4.setJokerCards(jokers);
        assertEquals(jokers, player4.getJokerCards());
    }

    @Test
    void testSetJokerCards(){
        List<JokerCard> jokers = new ArrayList<>();
        jokers.add(new AdditionalPointsJoker());
        Player player4 = new Player(4L, "test", 123);
        player4.setJokerCards(jokers);
        assertEquals(jokers, player4.getJokerCards());
    }

    @Test
    void testEmptyConstructor(){
        Player pp = new Player();
        assertNotNull(pp);
    }

    @Test
    void getId() {
        assertEquals(1L, player1.getId());
    }

    @Test
    void getId2() {
        assertEquals(2L, player2.getId());
    }

    @Test
    void setId() {
        player3.setId(6L);
        assertEquals(6L, player3.getId());
    }

    @Test
    void getUsername() {
        assertEquals("rafael1234", player1.getUsername());
    }

    @Test
    void setUsername() {
        player2.setUsername("somethingElse");
        assertEquals("somethingElse", player2.getUsername());
    }

    @Test
    void getCurrentScore() {
        Player player4 = new Player(4L, "test", 123);
        assertEquals(123, player4.getCurrentScore());
    }

    @Test
    void setCurrentScore() {
        Player player4 = new Player(4L, "test", 123);
        player4.setCurrentScore(555);
        assertEquals(555, player4.getCurrentScore());
    }

    @Test
    void deleteJoker() {
        List<JokerCard> jokers = new ArrayList<>();
        JokerCard additionalPoints = new AdditionalPointsJoker();
        JokerCard questionChange = new QuestionChangeJoker();
        jokers.add(additionalPoints);
        jokers.add(questionChange);
        Player player4 = new Player(4L, "test", 123);
        player4.setJokerCards(jokers);
        player4.deleteJoker(additionalPoints);
        List<JokerCard> expected = new ArrayList<>();
        expected.add(questionChange);
        assertEquals(expected, player4.getJokerCards());
    }
}



