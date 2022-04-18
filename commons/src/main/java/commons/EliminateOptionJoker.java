package commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EliminateOptionJoker extends JokerCard{
    private Question question;

    public EliminateOptionJoker(String name, String description,
                                boolean onlyMultiplayer, MultipleChoiceQuestion question) {
        super(name, description, onlyMultiplayer);
        this.question = question;
    }
    public EliminateOptionJoker( Question question) {
        super("Eliminate Option Joker", "....", false);
        this.question = question;
    }

    /**
     * This method deletes one of the wrong answers in the MultipleChoiceQuestion.
     */
    @Override
    public void useCard() {

        if(question instanceof MultipleChoiceQuestion)
        {
            useCardMultipleChoice();
        }

        if(question instanceof MostEnergyQuestion)
        {
            useCardMostEnergy();
        }
    }

    private void useCardMostEnergy() {
        Activity correct = ((MostEnergyQuestion)question).getCorrectAnswer();

        List<Activity> options = ((MostEnergyQuestion)question).getOtherActivities();
       if(options.get(0).equals(correct)){
           options.remove(1);
       }
       else{
           options.remove(0);
       }

    }

    /**
     *
     */
    public void useCardMultipleChoice(){
        ArrayList<Long> options = ((MultipleChoiceQuestion)this.question).getOptions();
        long correctOption = this.question.getActivity().getConsumption_in_wh();
        long optionToDelete = options.get(0);
        //first two elements in the list are always wrong according to Som's implementation
        //just in case, additional check:
        if(optionToDelete == correctOption){
            optionToDelete = options.get(1);
        }
        options.remove(optionToDelete);
    }


    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EliminateOptionJoker that = (EliminateOptionJoker) o;
        return Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), question);
    }

    @Override
    public String toString() {
        return "EliminateOptionJoker{" +
                "question=" + question +
                '}';
    }
}
