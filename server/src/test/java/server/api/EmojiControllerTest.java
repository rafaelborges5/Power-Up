package server.api;

import commons.Emoji;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmojiControllerTest {

    private EmojiController underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new EmojiController();
    }
    Emoji e = new Emoji();

    @Test
    void addEmoji() {
        assertEquals(e, underTest.addEmoji(e));
    }
}