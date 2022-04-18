package server.sevice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.PlayerRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;
    private PlayerService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new PlayerService(playerRepository);
    }

    @Test
    void getPlayers() {
        underTest.getPlayers();
        verify(playerRepository).findAll();
    }

    @Test
    void addPlayer() {
        underTest.addPlayer(null);
        verify(playerRepository).save(null);
    }

    @Test
    void deletePlayer() {
        when(playerRepository.existsById(1L)).thenReturn(true);
        underTest.deletePlayer(1L);
        verify(playerRepository).deleteById(1L);
    }
}