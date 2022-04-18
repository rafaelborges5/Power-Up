package server.sevice;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.api.ActivityController;

import java.util.ArrayList;

@Service
public class QuestionService {

    private final ActivityController activityController;

    private final int globalTimeAllowed = 15;

    @Autowired
    public QuestionService(ActivityController activityController) {
        this.activityController = activityController;
    }


    /**
     * This method will get a random MultipleChoiceQuestion. It will request a random activity from the database through
     * the activityController
     * @return the MultipleChoiceQuestion
     */
    public Question getRandomMultipleChoiceQuestion() {
        Activity activity = activityController.getRandomActivity();
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(activity, 100, globalTimeAllowed);
        return multipleChoiceQuestion;
    }

    /**
     * This method will get a random GuessQuestion. It will request a random activity from the database through the
     * activityController
     * @return the GuessQuestion
     */
    public Question getRandomGuessQuestion() {
        Activity activity = activityController.getRandomActivity();
        GuessQuestion guessQuestion = new GuessQuestion(activity, 100, globalTimeAllowed);
        return guessQuestion;
    }

    /**
     * This method will get a random InsteadOfQuestion. It will request the main activity from the database through the
     * activityController and then will request the other activities (also random) to prepare the options. The ratio
     * is done in the InsteadOfQuestion class. It is guaranteed that the other options have a smaller consumption than
     * the main activity.
     * @return the InsteadOfQuestion
     */
    public Question getRandomInsteadOfQuestion() {
        Activity activity = activityController.getRandomActivity();
        long maxConsumption  = activity.getConsumption_in_wh();
        ArrayList<Activity> options = new ArrayList<>();
        //TODO maxConsumption = 10 * consumption.
        //TODO find activities within some given range
        //TODO: still rendering "0 times".
        for (int i = 0; i < 3; i++) {
            Activity option = activityController.getRandomActivity();
            if (options.contains(option) || option.equals(activity) || option.getConsumption_in_wh() > maxConsumption) {
                i--;
                continue;
            } else {
                options.add(option);
            }
        }
        return new InsteadOfQuestion(activity, 100, globalTimeAllowed, options);
    }

    /**
     * This method will get a random MostEnergyQuestion. It wil request the main activity from the database through
     * the activityController and then will request the other activities (also random) to prepare the options
     * @return the MostEnergyQuestion
     */
    public Question getRandomMostEnergyQuestion() {
        Activity activity = activityController.getRandomActivity();
        ArrayList<Activity> options = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Activity option = activityController.getRandomActivity();
            if (options.contains(option) || options.contains(activity)) {
                i--;
                continue;
            } else {
                options.add(option);
            }
        }
        return new MostEnergyQuestion(activity, 100, globalTimeAllowed, options);
    }
}
