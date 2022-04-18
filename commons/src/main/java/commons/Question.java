package commons;

import java.util.Objects;

public abstract class Question {

    private Activity activity;
    private int availablePoints;
    private String difficulty;
    private int allowedTime;    // maximum allowed time for this question
    private boolean isChosenAnswerCorrect;

    public Question(Activity activity, int availablePoints, String difficulty, int allowedTime) {
        this.activity = activity;
        this.availablePoints = availablePoints;
        this.difficulty = difficulty;
        this.allowedTime = allowedTime;
    }

    public Question(Activity activity, int availablePoints, int allowedTime) {
        this.activity = activity;
        this.availablePoints = availablePoints;
        this.allowedTime = allowedTime;
    }

    public Question () {
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getAvailablePoints() {
        return availablePoints;
    }

    public void setAvailablePoints(int availablePoints) {
        this.availablePoints = availablePoints;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getAllowedTime() {
        return allowedTime;
    }

    public void setAllowedTime(int allowedTime) {
        this.allowedTime = allowedTime;
    }

    public boolean isChosenAnswerCorrect() {
        return isChosenAnswerCorrect;
    }

    public void setChosenAnswerCorrect(boolean chosenAnswerCorrect) {
        isChosenAnswerCorrect = chosenAnswerCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return getAvailablePoints() == question.getAvailablePoints() && getAllowedTime() == question.getAllowedTime()
                && getActivity().equals(question.getActivity()) && getDifficulty().equals(question.getDifficulty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActivity(), getAvailablePoints(), getDifficulty(), getAllowedTime());
    }

    @Override
    public String toString() {
        return "Question{" +
                "activity=" + activity +
                ", availablePoints=" + availablePoints +
                ", difficulty='" + difficulty + '\'' +
                ", allowedTime=" + allowedTime +
                '}';
    }
}
