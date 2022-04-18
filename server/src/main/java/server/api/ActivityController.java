package server.api;

import commons.Activity;
import commons.ImagePacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.sevice.ActivityService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/activity")
public class ActivityController {
    /**
     * API layer of the Activity class.
     */
    private final ActivityService activityService;

    /**
     * Creates an instance of ActivityController Class
     * @param activityService    An instance of the utility class that links the API Layer to the Repository Layer
     */
    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * Serves the user's GET request by invoking the getActivities() method from the activityService object
     * @return List of activities returned by the service layer.
     */
    @GetMapping
    public List<Activity> getActivities(){
        return activityService.getActivities();
    }

    /**
     * This method will update an Activity with the same Id as the argument to the corresponding fields present in the
     * argument
     * @param activity to update
     * @return the updated Activity
     */
    @PutMapping
    public Activity updateActivity(@RequestBody Activity activity) {
        return activityService.updateActivity(activity);
    }

    /**
     *  API layer method for the POST request
     * @param activity An instance of the utility class that links the API Layer to the Repository Layer
     */
    @PostMapping
    public void addNewActivity( @RequestBody Activity activity){ activityService.addActivity(activity);
    }

    /**
     *  API layer method for DELETE request.
     * @param activityId ID of the activity to be deleted.
     */
    @DeleteMapping(path="{activityId}")
    public void deleteActivity(@PathVariable("activityId") String activityId) {
        activityService.deleteActivity(activityId);
    }

    /**
     * This method will get an Activity by Id
     * @param activityId to retrieve for
     * @return the Activity with such id
     */
    @GetMapping(path = "{activityId}")
    public Activity getActivityById(@PathVariable("activityId") String activityId) {
        return activityService.getActivityById(activityId);
    }

    /**
     * This method will pick a random activity from the database
     * @return the activity
     */
    public Activity getRandomActivity() {
        return activityService.getRandomActivity();
    }

    /**
     * This method is the endpoint for the uploading of Images.
     * @param file with he serialized image
     * @throws IOException
     */
    @PostMapping("/image")
    public void uploadImage(@RequestBody ImagePacket file) throws IOException {
        activityService.processImagePacket(file);
    }
}
