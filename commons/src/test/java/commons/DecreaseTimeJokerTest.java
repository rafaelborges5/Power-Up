package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecreaseTimeJokerTest {

    DecreaseTimeJoker decreaseTimeJoker;
    DecreaseTimeJoker decreaseTimeJoker2;
    Player player;
    Question question;

    @BeforeEach
    void setUp() {
        player = new Player("noname", 15);
        Activity activity = new Activity("00-shower",
                "00/shower.png",
                "Taking a hot shower for 6 minutes",
                4000,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
        question = new MultipleChoiceQuestion(activity, 10,"EASY", 20);
        decreaseTimeJoker = new DecreaseTimeJoker("aaa");
    }

    @Test
    void getSenderUsername() {
        assertEquals("aaa", decreaseTimeJoker.getSenderUsername());
    }

    @Test
    void setSenderUsername() {
        decreaseTimeJoker.setSenderUsername("aa");
        assertEquals("aa", decreaseTimeJoker.getSenderUsername());
    }

    @Test
    void returnUseCard() {
        decreaseTimeJoker.setLocalPlayer(new Player("as", 33));
        assertEquals(5, decreaseTimeJoker.returnUseCard());
    }

    @Test
    void useCard() {
        decreaseTimeJoker.setLocalPlayer(new Player("noname", 33));
        decreaseTimeJoker.useCard();
        assertEquals(15, decreaseTimeJoker.getLocalPlayer().getTimeLeft());
    }

    @Test
    void setLocalPlayer() {
        Player p = new Player("noname", 33);
        decreaseTimeJoker.setLocalPlayer(p);
        assertEquals(p, decreaseTimeJoker.getLocalPlayer());
    }
}