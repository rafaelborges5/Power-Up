package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JokerAlertTest {
    private String username1;
    private String joker1;
    private String username2;
    private String joker2;
    private JokerAlert alert;
    private JokerAlert alert2;

    @BeforeEach
    void setUp() {
        this.username1 = "teesting";
        this.joker1 = "Additional points";
        this.username2 = "boss";
        this.joker2 = "Decrease time";
        this.alert = new JokerAlert(username1, joker1);
        this.alert2 = new JokerAlert(username2, joker2);
    }

    @Test
    void constructorTest(){
        JokerAlert testAlert = new JokerAlert();
        assertNotNull(testAlert);
    }
    @Test
    void getSenderUsername() {
        assertEquals("teesting", alert.getSenderUsername());
    }

    @Test
    void setSenderUsername() {
        alert.setSenderUsername("int");
        assertEquals("int", alert.getSenderUsername());
    }

    @Test
    void getJokerType() {
        assertEquals("Decrease time", alert2.getJokerType());
    }

    @Test
    void setJokerType() {
        alert.setJokerType("another");
        assertEquals("another", alert.getJokerType());
    }
}