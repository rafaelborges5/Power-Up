package server.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.sevice.QuestionService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    @Mock private QuestionService questionService;
    private QuestionController underTest;

    @BeforeEach
    void setUp() {
        underTest = new QuestionController(questionService);
    }


    @Test
    void getRandomQuestion1() {
        underTest.getRandomQuestion("guess");
        verify(questionService).getRandomGuessQuestion();
    }

    @Test
    void getRandomQuestion2() {
        underTest.getRandomQuestion("mostEnergy");
        verify(questionService).getRandomMostEnergyQuestion();
    }

    @Test
    void getRandomQuestion3() {
        underTest.getRandomQuestion("insteadOf");
        verify(questionService).getRandomInsteadOfQuestion();
    }

    @Test
    void getRandomQuestion4() {
        underTest.getRandomQuestion("multipleChoice");
        verify(questionService).getRandomMultipleChoiceQuestion()   ;
    }
}