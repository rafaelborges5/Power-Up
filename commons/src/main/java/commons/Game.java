package commons;


import java.util.ArrayList;
import java.util.Objects;


public abstract class Game {
    private ArrayList<Question> questions;  // this is the list of questions that players will be able to view
    // throughout the game
    private ArrayList<JokerCard> jokerCards;    // the list of all jokers that are available
    private int currentQuestionNumber; //int representing the index of the current question that is displayed
    private boolean gameOver = false;

    public Game(ArrayList<Question> questions, ArrayList<JokerCard> jokerCards) {
        this.questions = questions;
        this.jokerCards = jokerCards;
        currentQuestionNumber = 1;
    }

    public Game() {}

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<JokerCard> getJokerCards() {
        return jokerCards;
    }

    public void setCurrentQuestionNumber(int currentQuestionNumber) {
        this.currentQuestionNumber = currentQuestionNumber;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return Objects.equals(getQuestions(), game.getQuestions()) && Objects.equals(getJokerCards(),
                game.getJokerCards());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestions(), getJokerCards());
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
