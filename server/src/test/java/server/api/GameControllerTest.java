package server.api;

import commons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.sevice.GameService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock private GameService gameService;
    private GameController underTest;

    @BeforeEach
    void setUp() {
        underTest = new GameController(gameService);
    }

    @Test
    void getListMostEnergy() {
        List<MostEnergyQuestion> q = underTest.getListMostEnergy();
        verify(gameService).getListMostEnergy();
    }

    @Test
    void getListGuess() {
        List<GuessQuestion> q = underTest.getListGuess();
        verify(gameService).getListGuessQuestion();
    }

    @Test
    void getListMultipleChoice() {
        List<MultipleChoiceQuestion> q = underTest.getListMultipleChoice();
        verify(gameService).getListMultipleChoice();
    }

    @Test
    void getListInsteadOf() {
        List<InsteadOfQuestion> q = underTest.getListInsteadOf();
        verify(gameService).getListInsteadOf();
    }

    @Test
    void propagateNewPlayer() {
        when(gameService.getCurrentMultiGame()).thenReturn(new MultiPlayerGame(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>()));
        underTest.propagateNewPlayer(null);
        verify(gameService).getCurrentMultiGame();
    }

    @Test
    void getListPlayers() {
        when(gameService.getCurrentMultiGame()).thenReturn(new MultiPlayerGame(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>()));
        underTest.getCurrentListPlayers();
        verify(gameService).getCurrentMultiGame();
    }

    @Test
    void getCurrentListMostEnergy() {
        when(gameService.getCurrentMultiGame()).thenReturn(new MultiPlayerGame(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>()));
        underTest.getCurrentListMostEnergy();
        verify(gameService).getCurrentMultiGame();
    }

    @Test
    void getCurrentListGuess() {
        when(gameService.getCurrentMultiGame()).thenReturn(new MultiPlayerGame(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>()));
        underTest.getCurrentListGuess();
        verify(gameService).getCurrentMultiGame();
    }

    @Test
    void getListCurrentMultipleChoice() {
        when(gameService.getCurrentMultiGame()).thenReturn(new MultiPlayerGame(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>()));
        underTest.getListCurrentMultipleChoice();
        verify(gameService).getCurrentMultiGame();
    }

    @Test
    void getCurrentListInsteadOf() {
        when(gameService.getCurrentMultiGame()).thenReturn(new MultiPlayerGame(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>()));
        underTest.getCurrentListInsteadOf();
        verify(gameService).getCurrentMultiGame();
    }
}