package commons;

import java.util.ArrayList;
import java.util.Objects;

public class MultipleChoiceQuestion extends Question{

    private ArrayList<Long> options;
    private long correctAnswer;


    /**
     * Creates an instance of MultipleChoiceQuestion.
     * @param activity Activity to be used in the question.
     * @param difficulty      Difficulty of the question. This will determine the range of options that will be given
     *                        to the user.
     * @param availablePoints Maximum number of points that can be obtained by answering the question.
     * @param allowedTime Maximum time allowed for this question.
     */
    public MultipleChoiceQuestion(Activity activity, int availablePoints, String difficulty, int allowedTime) {
        super(activity, availablePoints, difficulty, allowedTime);


        // create a range of answers
        this.correctAnswer = activity.getConsumption_in_wh();
        ArrayList<Long> options;
        switch (difficulty){
            case "EASY":
                options = generateRandomNumbers((long)(correctAnswer*0.8), (long)(correctAnswer*1.2));
                break;
            case "MEDIUM":
                options = generateRandomNumbers((long)(correctAnswer*0.9), (long)(correctAnswer*1.1));
                break;
            case "HARD":
                options = generateRandomNumbers((long)(correctAnswer*0.95), (long)(correctAnswer*1.05));
                break;
            default:
                throw new IllegalArgumentException("You did not specify a valid difficulty");
        }


        options.add(correctAnswer);
        this.options = options;
    }

    /**
     * Creates a new MultipleChoiceQuestion instance if no difficulty is provided. By default, the difficulty is "EASY".
     * @param activity Activity to be used in the question.
     * @param availablePoints Maximum number of points that can be obtained by answering the question.
     * @param allowedTime Maximum time allowed for this question.
     */
    public MultipleChoiceQuestion(Activity activity, int availablePoints, int allowedTime) {
        super(activity, availablePoints, allowedTime);
        this.setDifficulty("EASY");
        // create a range of answers
        this.correctAnswer = activity.getConsumption_in_wh();
        ArrayList<Long> options = generateRandomNumbers((long) (correctAnswer * 0.8),
                (long)(correctAnswer * 1.2));

        options.add(correctAnswer);
        this.options = options;
    }

    public MultipleChoiceQuestion() {

    }

    /**
     * Private utility method used by the constructor.
     *
     * @param lowerBound Lower bound.
     * @param upperBound Upper bound.
     * @return An ArrayList of two distinct random numbers between lowerBound and upperBound.
     */
    private ArrayList<Long> generateRandomNumbers(long lowerBound, long upperBound) {
        // generate two random numbers within a given range.

        // find the number of trailing zeros

        long numOfTrailingZeros = countTrailingZeros(correctAnswer);

        // 560
        // 632
        // 521

        // 4500
        // 4312
        // 4817

        // edge case:
        // 4500
        // 4512
        // 4527



        long range = upperBound - lowerBound;

        long optionOne = this.correctAnswer;

        int i = 5;
        while (optionOne == this.correctAnswer && i > 0) {
            optionOne = roundToNearest((long) (Math.random() * range) + lowerBound, numOfTrailingZeros);
            i --;
        }

        if (i==0) {
            optionOne = correctAnswer + (long)Math.pow(10, numOfTrailingZeros);
        }


        long optionTwo = optionOne;

        int j = 5;
        while ((optionTwo == optionOne || optionTwo == this.correctAnswer) && j>0) {
            optionTwo = roundToNearest((long) (Math.random() * range) + lowerBound, numOfTrailingZeros);
            j--;
        }

        if (j==0) {
            optionTwo = correctAnswer - (long)Math.pow(10, numOfTrailingZeros);
        }

        if (optionTwo == optionOne) {
            optionTwo = correctAnswer + (long)Math.pow(10, numOfTrailingZeros);
        }


        ArrayList<Long> returnable = new ArrayList<Long>();
        returnable.add(optionOne);
        returnable.add(optionTwo);
        return returnable;


    }

    /**
     * Utility method that truncates a Long to the nearest multiple of 10 ^ base.
     * @param x Long to be rounded.
     * @param base Power of 10 to be used as a base.
     * @return Long Truncated number to the nearest multiple of 10 ^ base.
     */
    private long roundToNearest(long x, long base) {
        long divisor = (long)Math.pow(10, base);
        // divide x by divisor

        return x - (x % divisor);
    }

    /**
     * Utility method that counts the number of trailing zeros of a long.
     * @param n Number to be processed.
     * @return Number of trailing zeros of n.
     */
    private int countTrailingZeros(long n) {
        int i = 0;
        if (n == 0) {
            return 0;
        }
        while (n % 10 == 0) {
            i++;
            n /= 10;
        }
        return i;
    }


    public ArrayList<Long> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Long> options) {
        this.options = options;
    }

    public void setCorrectAnswer(long correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Equals method between two instances of the MultipleChoiceQuestion class, not including the ArrayList options
     * as this is
     * automatically generated.
     * @param o Object to be compared with
     * @return TRUE if objects have equal attributes, FALSE otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultipleChoiceQuestion that = (MultipleChoiceQuestion) o;
        return (Objects.equals(options, that.options) && super.equals(o));
    }

    public double getCorrectAnswer() {
        return correctAnswer;
    }

}



