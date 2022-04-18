package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmojiTest {

    Emoji e;

    @BeforeEach
    void setUp() {
        e = new Emoji("a", "aa");
    }

    @Test
    void testConstructor() {
        Emoji u = new Emoji();
        assertNotNull(u);
    }

    @Test
    void getSender() {
        assertEquals("a", e.getSender());
    }

    @Test
    void setSender() {
        e.setSender("b");
        assertEquals("b", e.getSender());
    }

    @Test
    void getEmojiPath() {
        assertEquals("aa", e.getEmojiPath());
    }

    @Test
    void setEmojiPath() {
        e.setEmojiPath("bb");
        assertEquals("bb", e.getEmojiPath());
    }
}