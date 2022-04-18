package commons;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class InsteadOfQuestion extends Question{


    private ArrayList<Activity> options;
    private Activity correctAnswer;

    /**
     * Creates an instance of InsteadOfQuestion.
     * Sets the correct answer to the 1st options. (It is shuffled later on)
     * @param activity Activity to be used in the question.
     * @param difficulty      Difficulty of the question. This will determine the range of options that will be given
     *                        to the user.
     * @param availablePoints Maximum number of points that can be obtained by answering the question.
     * @param allowedTime Maximum time allowed for this question.
     * @param options Other activities shown to the player.
     */
    public InsteadOfQuestion(Activity activity, int availablePoints, String difficulty, int allowedTime,
                             ArrayList<Activity> options) {
        super(activity, availablePoints, difficulty, allowedTime);
        this.options = options;
        this.correctAnswer = options.get(0);
    }

    /**
     * Creates a new InsteadOfQuestion instance if no difficulty is provided. By default, the difficulty is "EASY".
     * Sets the correct answer to the 1st options. (It is shuffled later on)
     * @param activity Activity to be used in the question.
     * @param availablePoints Maximum number of points that can be obtained by answering the question.
     * @param allowedTime Maximum time allowed for this question.
     * @param options Other activities shown to the player;
     */
    public InsteadOfQuestion(Activity activity, int availablePoints, int allowedTime, ArrayList<Activity> options) {
        super(activity, availablePoints, allowedTime);
        this.setDifficulty("EASY");
        this.options = options;
        this.correctAnswer = options.get(0);
    }

    public InsteadOfQuestion() {}

    /**
     * Compares this activity with another activity.
     * @param other Activity to be compared with.
     * @return int How many times the second activity can be done using the same consumption as this activity.
     */
    public int getCorrectRatio(Activity other) {
        double thisConsumption = this.getActivity().getConsumption_in_wh();
        double  otherConsumption = other.getConsumption_in_wh();

        return (int) (thisConsumption/otherConsumption);
    }

    /**
     * Generates the wrong ratio by comparing two activities and changing the final result
     * @param other Activity to compare to
     * @return ratio
     */
    public int getWrongRatio(Activity other){
        long thisConsumption = this.getActivity().getConsumption_in_wh();
        long otherConsumption = other.getConsumption_in_wh();

        double randomizedRatio = myRandom(1.3, 3.0);

        return (int)(randomizedRatio * thisConsumption/otherConsumption);
    }

    /**
     * This method return random number between given min and max
     * @param min Lower bound
     * @param max Upper bound
     * @return Random number in given range
     */
    public double myRandom(double min, double max) {
        Random r = new Random();
        return (r.nextInt((int)((max-min)*10+1))+min*10) / 10.0;
    }

    public Activity getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Activity correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Compares and returns a String representing how many times another activity can be done using the same
     * consumption as this activity.
     * @param other Activity to be compared with.
     * @return String representing how many times another activity can be done using the same consumption as this
     * activity.
     */
    public String substituteRatio(Activity other) {
        return this.getWrongRatio(other) + " times";
    }


    @Override
    public String toString() {
        return "InsteadOfQuestion{" +
                "options=" + options +
                ", correctAnswer=" + correctAnswer +
                '}';
    }

    public ArrayList<Activity> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Activity> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InsteadOfQuestion)) return false;
        if (!super.equals(o)) return false;
        InsteadOfQuestion that = (InsteadOfQuestion) o;
        return getOptions().equals(that.getOptions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOptions());
    }
}
