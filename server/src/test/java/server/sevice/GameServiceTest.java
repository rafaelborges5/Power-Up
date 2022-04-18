package server.sevice;

import commons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.api.QuestionController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private QuestionController questionController;
    private GameService underTest;

    @BeforeEach
    void setUp() {
        underTest = new GameService(questionController);
    }

    @Test
    void instantiateMultiGame() {
        when(questionController.getRandomQuestion("mostEnergy")).thenReturn(mostEnergyQuestionList().get(0),
                mostEnergyQuestionList().get(1), mostEnergyQuestionList().get(2), mostEnergyQuestionList().get(3),
                mostEnergyQuestionList().get(4));
        when(questionController.getRandomQuestion("guess")).thenReturn(guessQuestionList().get(0),
                guessQuestionList().get(1), guessQuestionList().get(2), guessQuestionList().get(3),
                guessQuestionList().get(4));
        when(questionController.getRandomQuestion("multipleChoice")).thenReturn(multipleChoiceQuestionList().get(0)
                , multipleChoiceQuestionList().get(1), multipleChoiceQuestionList().get(2),
                multipleChoiceQuestionList().get(3), multipleChoiceQuestionList().get(4),
                multipleChoiceQuestionList().get(5));
        when(questionController.getRandomQuestion("insteadOf")).thenReturn(insteadOfQuestionList().get(0),
                insteadOfQuestionList().get(1), insteadOfQuestionList().get(2), insteadOfQuestionList().get(3),
                insteadOfQuestionList().get(4));
        MultiPlayerGame game = underTest.instantiateMultiGame();
        List<Question> questions = game.getQuestions();
        List<Question> toCompare = new ArrayList<>();
        toCompare.addAll(guessQuestionList());
        toCompare.addAll(insteadOfQuestionList());
        toCompare.addAll(mostEnergyQuestionList());
        toCompare.addAll(multipleChoiceQuestionList());
        assertEquals(questions, toCompare);
    }

    @Test
    void getListMostEnergy() {
        when(questionController.getRandomQuestion("mostEnergy")).thenReturn(mostEnergyQuestionList().get(0),
                mostEnergyQuestionList().get(1), mostEnergyQuestionList().get(2), mostEnergyQuestionList().get(3),
                mostEnergyQuestionList().get(4));
        assertEquals(underTest.getListMostEnergy(), mostEnergyQuestionList());
    }

    @Test
    void getListGuessQuestion() {
        when(questionController.getRandomQuestion("guess")).thenReturn(guessQuestionList().get(0),
                guessQuestionList().get(1), guessQuestionList().get(2), guessQuestionList().get(3),
                guessQuestionList().get(4));
        assertEquals(underTest.getListGuessQuestion(), guessQuestionList());
    }

    @Test
    void getListMultipleChoice() {
        when(questionController.getRandomQuestion("multipleChoice")).thenReturn(multipleChoiceQuestionList().get(0)
                , multipleChoiceQuestionList().get(1), multipleChoiceQuestionList().get(2), multipleChoiceQuestionList()
                        .get(3), multipleChoiceQuestionList().get(4), multipleChoiceQuestionList().get(5));
        List<MultipleChoiceQuestion> q = underTest.getListMultipleChoice();
        List<MultipleChoiceQuestion> q1 = multipleChoiceQuestionList();
        assertEquals(q, q1);
    }


    @Test
    void getListInsteadOf() {
        List<InsteadOfQuestion> list = insteadOfQuestionList();
        when(questionController.getRandomQuestion("insteadOf")).thenReturn(insteadOfQuestionList().get(0),
                insteadOfQuestionList().get(1), insteadOfQuestionList().get(2), insteadOfQuestionList().get(3),
                insteadOfQuestionList().get(4));
        List<InsteadOfQuestion> q = underTest.getListInsteadOf();
        List<InsteadOfQuestion> q1 = insteadOfQuestionList();
        assertEquals(q, q1);
    }

    public static List<MostEnergyQuestion> mostEnergyQuestionList() {
        List<MostEnergyQuestion> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MostEnergyQuestion m = mostEnergyQuestion();
            m.setAllowedTime(i);
            list.add(m);
        }
        return list;
    }

    public static List<MultipleChoiceQuestion> multipleChoiceQuestionList() {
        List<MultipleChoiceQuestion> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            MultipleChoiceQuestion m = multipleChoiceQuestion();
            m.setAllowedTime(i);
            m.setAvailablePoints(3 * i);
            m.setOptions(new ArrayList<>());
            list.add(m);
        }
        return list;
    }

    public static List<GuessQuestion> guessQuestionList() {
        List<GuessQuestion> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            GuessQuestion m = guessQuestion();
            m.setAllowedTime(i);
            list.add(m);
        }
        return list;
    }

    public static List<InsteadOfQuestion> insteadOfQuestionList() {
        List<InsteadOfQuestion> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            InsteadOfQuestion m = insteadOfQuestion();
            m.setAllowedTime(i);
            list.add(m);
        }
        return list;
    }

    public static MostEnergyQuestion mostEnergyQuestion() {
        return new MostEnergyQuestion(new Activity(1L, "adf", 12), 1, "EASY", 1, new ArrayList<>());
    }

    public static MultipleChoiceQuestion multipleChoiceQuestion() {
        return new MultipleChoiceQuestion(new Activity(1L, "adf", 12), 1, "EASY", 1);
    }

    public static InsteadOfQuestion insteadOfQuestion() {
        Activity a = new Activity(1L, "ads", 12);
        InsteadOfQuestion i = new InsteadOfQuestion();
        i.setActivity(a);
        i.setAllowedTime(12);
        i.setAvailablePoints(12);
        i.setDifficulty("EASY");
        i.setOptions(new ArrayList<>());
        return i;
    }

    public static GuessQuestion guessQuestion() {
        return new GuessQuestion(new Activity(1L, "adf", 12), 1, "EASY", 1);
    }
}