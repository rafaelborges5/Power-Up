package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdditionalPointsTest {

    AdditionalPointsJoker additionalPointsJoker1;
    AdditionalPointsJoker additionalPointsJoker2;
    Player player;
    Question question;

    @BeforeEach
    public void setup(){
        player = new Player("noname", 15);
        Activity activity = new Activity("00-shower",
                "00/shower.png",
                "Taking a hot shower for 6 minutes",
                4000,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
        question = new MultipleChoiceQuestion(activity, 10,"EASY", 20);
        additionalPointsJoker1 = new AdditionalPointsJoker("Additional",
                "Adds 10 additional points if you answer correctly",
                false, player, question);
        additionalPointsJoker2 = new AdditionalPointsJoker("Additional",
                "Adds 10 additional points if you answer correctly",
                false, player, question);



    }

    @Test
    void testConstructor() {
        AdditionalPointsJoker joker = new AdditionalPointsJoker(new Player("asdf", 44));
        assertNotNull(joker);
    }

    @Test
    void useCard() {
        additionalPointsJoker1.useCard();
        assertEquals(15, player.getCurrentScore());
    }

    @Test
    void useCard2() {
        Player testPlayer = new Player("noname", 20);
        Activity testActivity = new Activity("00-shower",
                "00/shower.png",
                "Some title",
                2137,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
        Question testQuestion = new MultipleChoiceQuestion(testActivity, 100,"EASY", 20);
        AdditionalPointsJoker testJoker = new AdditionalPointsJoker("Additional",
                "Adds 10 additional points if you answer correctly",
                false, testPlayer, testQuestion);
        testJoker.useCard();
        assertEquals(200, testQuestion.getAvailablePoints());
    }

    @Test
    void useCard3() {
        Player testPlayer = new Player("noname", 20);
        Activity testActivity = new Activity("00-shower",
                "00/shower.png",
                "Some title",
                2137,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
        Question testQuestion = new MultipleChoiceQuestion(testActivity, 100,"EASY", 20);
        AdditionalPointsJoker testJoker = new AdditionalPointsJoker("Additional",
                "Adds 10 additional points if you answer correctly",
                false, testPlayer, testQuestion);
        testJoker.useCard();
        assertEquals(20, testPlayer.getCurrentScore());
    }

    @Test
    void testEquals() {
        assertEquals(additionalPointsJoker1, additionalPointsJoker2);
    }

    @Test
    void testNotEquals() {
        AdditionalPointsJoker additionalPointsJoker3 = new AdditionalPointsJoker("Additional",
                "different description",
                false,
                player, question);
        assertNotEquals(additionalPointsJoker1, additionalPointsJoker3);
    }

    @Test
    void testNullEquals() {
        AdditionalPointsJoker additionalPointsJoker3 = null;
        assertNotEquals(additionalPointsJoker1, additionalPointsJoker3);
    }

    @Test
    void testHashCode() {
        assertEquals(additionalPointsJoker1.hashCode(), additionalPointsJoker2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals(additionalPointsJoker1.toString(),
                "AdditionalPointsJoker{player=Player{id=null, username='noname', currentScore=15}, " +
                        "question=Question{activity=Activity{id='00-shower', image_path='00/shower.png', " +
                        "title='Taking a hot shower for 6 minutes', consumption_in_wh=4000, " +
                        "source='https://www.quora.com/" +
                        "How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower'}, " +
                        "availablePoints=10, difficulty='EASY', allowedTime=20}}");
    }
}