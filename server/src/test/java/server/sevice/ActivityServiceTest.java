package server.sevice;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.ActivityRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;
    private ActivityService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new ActivityService(activityRepository);
    }

    @Test
    void getActivities() {
        underTest.getActivities();
        verify(activityRepository).findAll();
    }

    @Test
    void addActivity() {
        underTest.addActivity(null);
        verify(activityRepository).save(null);
    }

    @Test
    void deleteActivity() {
        String id = getActivity().getId();
        when(activityRepository.existsById(id)).thenReturn(true);
        underTest.deleteActivity(id);
        verify(activityRepository).deleteById(id);
    }

    @Test
    void getActivityById() {
        String id = getActivity().getId();
        when(activityRepository.findById(id)).thenReturn(Optional.of(getActivity()));
        underTest.getActivityById(id);
        verify(activityRepository).findById(id);
    }

    @Test
    void getRandomActivity() {
        underTest.getRandomActivity();
        verify(activityRepository).findAll();
    }

    @Test
    void updateActivity() {
        String id = getActivity().getId();
        when(activityRepository.findById(id)).thenReturn(Optional.of(getActivity()));
        Activity returned = underTest.updateActivity(getActivity2());
        verify(activityRepository).findById(id);
        assertEquals(returned, getActivity2());
    }

    public static Activity getActivity(){
        return new Activity("00-shower",
                "00/shower.png",
                "Taking a hot shower for 6 minutes",
                4000,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
    }

    public static Activity getActivity2(){
        return new Activity("00-shower",
                "00/shower.png",
                "Taking a hot shower for 10 minutes",
                5000,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower-10");
    }
}