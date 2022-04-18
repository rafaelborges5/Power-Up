package server.sevice;

import commons.Activity;
import commons.ImagePacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    /**
     *  Creates an instance of this class ActivityService Class.
     * @param activityRepository   An instance of the repository class.
     */
    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }


    /**
     * Returns all the activities from the database.
     * @return List of Player instances found the in the repository.
     */
    public  List<Activity> getActivities() {return activityRepository.findAll();
    }

    /**
     * Service layer method for adding an activity to the repository.
     * @param activity An instance of the Activity Class  that will be added to the repository;
     */
    public void addActivity(Activity activity) {
        activityRepository.save(activity);
    }

    /**
     *  Service layer method for deleting a player from the repository.
     * @param activityId Unique ID of the activity to be deleted.
     */
    public void deleteActivity(String activityId) {
        boolean exists = activityRepository.existsById(activityId);
        if (!exists) {
            throw new IllegalStateException("activity with ID " + activityId + " does not exist.");
        }

        activityRepository.deleteById(activityId);
    }

    /**
     * This method will get an activity by Id
     * @param id to retrieve for
     * @return the Activity with such id
     */
    public Activity getActivityById(String id) {
        Optional<Activity> activity = activityRepository.findById(id);
        if(activity.isPresent()) {
            return activity.get();
        } else {
            throw new IllegalStateException("activity with ID " + id + " does not exist.");
        }
    }

    /**
     * This method will pick a random activity from the database. It currently retrieves all and then picks a random
     * one. Maybe there is a better way to do this
     * @return the random Activity
     */
    public Activity getRandomActivity() {
        List<Activity> list = activityRepository.findAll();
        if(list.size() == 0) return null;
        int random = (int) (Math.random() * list.size());
        return list.get(random);
    }

    /**
     * This method will update all the fields of the activity with Id = as the id present in the activity passed in as
     * an argument
     * @param activity to update for
     * @return the updated Activity
     */
    @Transactional
    public Activity updateActivity(Activity activity) {
        Optional<Activity> toUpdateOptional = activityRepository.findById(activity.getId());
        if(!toUpdateOptional.isPresent()) {
            throw new IllegalStateException("activity with ID " + activity.getId() + " does not exist.");
        }
        else {
            Activity toUpdate = toUpdateOptional.get();
            toUpdate.setConsumption_in_wh(activity.getConsumption_in_wh());
            toUpdate.setTitle(activity.getTitle());
            toUpdate.setSource(activity.getSource());
            return toUpdate;
        }
    }

    /**
     * This method will materialize the image in the byte array to the Extra directory of the Activity_bank
     * @param file that contains the serialized image and it's name
     * @throws IOException
     */
    public void processImagePacket(ImagePacket file) throws IOException {
        byte[] arr =  file.getByteArr();
        try {
            Files.write(Path.of("src/main/resources/Activity_bank/extra/", file.getFileName()), arr);
            Files.write(Path.of("build/resources/main/Activity_bank/extra/", file.getFileName()), arr);
        } catch (NoSuchFileException e) {
            try {
                Files.write(Path.of("server/src/main/resources/Activity_bank/extra/", file.getFileName()), arr);
                Files.write(Path.of("server/build/resources/main/Activity_bank/extra/", file.getFileName()), arr);
            } catch (Exception e1) {
                e.printStackTrace();
            }
        }

    }



}
