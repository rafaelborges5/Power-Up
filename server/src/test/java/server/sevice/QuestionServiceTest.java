package server.sevice;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import server.api.ActivityController;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private ActivityController activityController;
    private QuestionService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new QuestionService(activityController);
    }

    @Test
    void getRandomMultipleChoiceQuestion() {
        Mockito.when(activityController.getRandomActivity()).thenReturn(getActivity());
        underTest.getRandomMultipleChoiceQuestion();
        verify(activityController).getRandomActivity();
    }

    @Test
    void getRandomGuessQuestion() {
        Mockito.when(activityController.getRandomActivity()).thenReturn(getActivity());
        underTest.getRandomGuessQuestion();
        verify(activityController).getRandomActivity();
    }

    @Test
    void getRandomInsteadOfQuestion() {
        Mockito.when(activityController.getRandomActivity()).thenReturn(getActivity(), getListActivities().get(0),
                getListActivities().get(1), getListActivities().get(2));
        underTest.getRandomInsteadOfQuestion();
        verify(activityController, times(4)).getRandomActivity();
    }

    @Test
    void getRandomMostEnergyQuestion() {
        Mockito.when(activityController.getRandomActivity()).thenReturn(getActivity(), getListActivities().get(0),
                getListActivities().get(1));
        underTest.getRandomMostEnergyQuestion();
        verify(activityController, times(3)).getRandomActivity();
    }

    public static Activity getActivity(){
        return new Activity("00-shower",
                "00/shower.png",
                "Taking a hot shower for 6 minutes",
                4000,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
    }

    public static List<Activity> getListActivities() {
        List<Activity> list = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            list.add(getActivity());
            list.get(i).setSource("" + i);
        }
        return list;
    }
}