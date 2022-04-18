package commons;

import java.util.Objects;

public class AdditionalPointsJoker extends JokerCard{

    private Player player;
    private Question question;
    private boolean usedJoker;

    public AdditionalPointsJoker(String name,
                                 String description,
                                 boolean onlyMultiplayer,
                                 Player player,
                                 Question question) {
        super(name, description, onlyMultiplayer);
        this.player = player;
        this.question = question;
        this.usedJoker = false;
    }
    public AdditionalPointsJoker(Player player) {
        super("Additional Points Joker", "....", false);
        this.player = player;
        this.usedJoker = false;
    }

    public AdditionalPointsJoker() {
        super("Additional Points Joker", "....", false);
        this.usedJoker = false;
    }

    /**
     * This method adds additional points for the player in case the answer is correct.
     * The amount of additional points is half of available points for this question.
     * TODO When we have the interaction with the client,
     * add an if-statement that will actually check whether the answer is correct.
     */
    @Override
    public void useCard() {
        System.out.println("old available points: " + this.question.getAvailablePoints());
        this.question.setAvailablePoints(2 * this.question.getAvailablePoints());
        System.out.println("new available points: " + this.question.getAvailablePoints());
        this.usedJoker = true;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isUsedJoker() {
        return usedJoker;
    }

    public void setUsedJoker(boolean usedJoker) {
        this.usedJoker = usedJoker;
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
        AdditionalPointsJoker that = (AdditionalPointsJoker) o;
        return Objects.equals(player, that.player) && Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player, question);
    }

    @Override
    public String toString() {
        return "AdditionalPointsJoker{" +
                "player=" + player +
                ", question=" + question +
                '}';
    }
}
