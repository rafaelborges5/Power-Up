package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EliminateOptionJokerTest {

    EliminateOptionJoker eliminate;
    Activity activity;
    MultipleChoiceQuestion question;


    @BeforeEach
    void setUp() {
        activity = new Activity("00-shower",
                "00/shower.png",
                "Taking a hot shower for 6 minutes",
                4000,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
        question = new MultipleChoiceQuestion(activity, 10,"EASY", 20);
        eliminate = new EliminateOptionJoker("name", "desc", false, question);
    }

    @Test
    void testConstructor() {
        EliminateOptionJoker j = new EliminateOptionJoker(new MultipleChoiceQuestion());
        assertNotNull(j);
    }

    @Test
    void useCard() {
        eliminate.useCard();
        assertTrue(question.getOptions().contains(question.getActivity().getConsumption_in_wh()));
        assertEquals(2, question.getOptions().size());
    }


    @Test
    void getQuestion() {
        assertEquals(eliminate.getQuestion(), question);
    }

    @Test
    void setQuestion() {
        Question question1 = new MultipleChoiceQuestion(activity, 20,"HARD", 100);
        eliminate.setQuestion((MultipleChoiceQuestion) question1);
        assertEquals(eliminate.getQuestion(), question1);
    }

    @Test
    void testEquals() {
        EliminateOptionJoker j = new EliminateOptionJoker(new MultipleChoiceQuestion());
        EliminateOptionJoker j1 = new EliminateOptionJoker("name", "desc", false, question);
        assertNotEquals(j, j1);
        assertEquals(j1, eliminate);
    }

    @Test
    void testHashCode() {
        EliminateOptionJoker eliminate2 = new EliminateOptionJoker("name", "desc", false, question);
        assertTrue(eliminate.hashCode() == eliminate2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals(eliminate.toString(),
                "EliminateOptionJoker{question=Question{activity=Activity{id='00-shower', " +
                        "image_path='00/shower.png', title='Taking a hot shower for 6 minutes', consumption_in_wh=" +
                        "4000, source='https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-" +
                        "shower'}, availablePoints=10, difficulty='EASY', allowedTime=20}}");
    }
}