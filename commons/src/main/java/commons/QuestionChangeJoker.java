package commons;

public class QuestionChangeJoker extends JokerCard{
    //private Game game


    public QuestionChangeJoker(String name, String description, boolean onlyMultiplayer) {
        super(name, description, onlyMultiplayer);
    }
    public QuestionChangeJoker() {
        super("Question Change Joker", "....", false);
    }




    /**
     * This method changes the question for the player that uses this card.
     * To implement it Game class is needed, we have to merge Som's implementation.
     * After we do that, I'll finish implementation for this card (with tests)
     */
    @Override
    public void useCard() {

    }


}
