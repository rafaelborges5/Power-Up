package server.api;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.ActivityRepositoryTest;
import server.sevice.ActivityService;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActivityControllerTest {
   private ActivityRepositoryTest myRepo;
   private ActivityController controller;
    @BeforeEach
    public void setup() {

        myRepo = new ActivityRepositoryTest();
        controller = new ActivityController(new ActivityService(myRepo));
    }

    @Test
    public void databaseIsUSed() {
        controller.addNewActivity(getActivity());
        assertTrue(myRepo.calledMethods.contains("save"));
    }
    @Test
    public void testGetActivities(){
        controller.addNewActivity(getActivity());
        controller.addNewActivity(getActivity2());
        List<Activity> localList  = List.of(getActivity(), getActivity2());
        List<Activity> repositoryList = controller.getActivities();
        assertEquals(localList,repositoryList);
    }

    @Test
    public void testDeleteActivities(){
        controller.addNewActivity(getActivity());
        List<Activity> localList  = new ArrayList<>();
        controller.deleteActivity("00-shower");
        List<Activity> repositoryList = controller.getActivities();
        assertEquals(localList,repositoryList);
    }

    @Test
    public void testGetRandomActivity() {
        controller.addNewActivity(getActivity());
        assertEquals(getActivity(), controller.getRandomActivity());
    }

    public static Activity getActivity(){
        return new Activity("00-shower",
                "00/shower.png",
                "Taking a hot shower for 6 minutes",
                4000,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
    }
    public static Activity getActivity2(){
        return new Activity("00-smartphone",
                "00/smartphone.png",
                "Charging your smartphone at night",
                10,
                "https://9to5mac.com/2021/09/16/iphone-13-battery-life/");
    }

}