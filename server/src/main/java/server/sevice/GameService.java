package server.sevice;

import commons.Question;
import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.api.QuestionController;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private final QuestionController questionController;

    private MultiPlayerGame currentMultiGame;
    private List<MultiPlayerGame> activeMultiGames;

    @Autowired
    public GameService(QuestionController questionController) {
        this.questionController = questionController;
        activeMultiGames = new ArrayList<>();
    }

    /**
     * This method will create a new MultiPlayerGame object. This is supposed to be used when the game that currently
     * has an active lobby is closed and started, so there is a need to open a new MultiPlayerGame to receive the next
     * players
     * @return the MultiPlayerGame
     */
    public MultiPlayerGame instantiateMultiGame() {
        ArrayList<Player> listOfPlayers = new ArrayList<>();
        ArrayList<Question> listOfQuestions = new ArrayList<>();
        listOfQuestions.addAll(getListGuessQuestion());
        listOfQuestions.addAll(getListInsteadOf());
        listOfQuestions.addAll(getListMostEnergy());
        listOfQuestions.addAll(getListMultipleChoice());
        return new MultiPlayerGame(listOfQuestions, new ArrayList<>(), listOfPlayers);
    }

    /**
     * This method will create a list of mostEnergyQuestions to return to the player
     * @return the list of MostEnergyQuestion
     */
    public ArrayList<MostEnergyQuestion> getListMostEnergy() {
        ArrayList<MostEnergyQuestion> questions = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Question q = questionController.getRandomQuestion("mostEnergy");
            if(questions.contains(q)) {
                i--;
                continue;
            } else {
                questions.add((MostEnergyQuestion) q);
            }
        }
        return questions;
    }

    /**
     * This method will create a list of GuessQuestion to return to the player
     * @return the list of GuessQuestion
     */
    public ArrayList<GuessQuestion> getListGuessQuestion() {
        ArrayList<GuessQuestion> questions = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Question q = questionController.getRandomQuestion("guess");
            if(questions.contains(q)) {
                i--;
                continue;
            } else {
                questions.add((GuessQuestion) q);
            }
        }
        return questions;
    }

    /**
     * This method will create a list of multipleChoiceQuestion to return to the player
     * @return the list of multipleChoiceQuestion
     */
    public ArrayList<MultipleChoiceQuestion> getListMultipleChoice() {
        ArrayList<MultipleChoiceQuestion> questions = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            Question q = questionController.getRandomQuestion("multipleChoice");
            if(questions.contains(q)) {
                i--;
                continue;
            } else {
                questions.add((MultipleChoiceQuestion) q);
            }
        }
        return questions;
    }

    /**
     * This method will create a list of insteadOfQuestion to return to the player
     * @return the list of insteadOfQuestion
     */
    public ArrayList<InsteadOfQuestion> getListInsteadOf() {
        ArrayList<InsteadOfQuestion> questions = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Question q = questionController.getRandomQuestion("insteadOf");
            if(questions.contains(q)) {
                i--;
                continue;
            } else {
                questions.add((InsteadOfQuestion) q);
            }
        }
        return questions;
    }

    /**
     * This method will return the MutliPlayerGame that currently has an active lobby
     * @return the MultiGame
     */
    public MultiPlayerGame getCurrentMultiGame() {
        if(this.currentMultiGame == null) currentMultiGame = instantiateMultiGame();
        return this.currentMultiGame;
    }

    /**
     * This method will close the lobby of the game that currently has an open lobby. It will archive it into the list
     * of active games, and it will create a new multiplayer game for the new lobby
     */
    public void archiveGame() {
        activeMultiGames.add(new MultiPlayerGame(currentMultiGame.getQuestions(), new ArrayList<>(),
            currentMultiGame.getPlayers()));
        currentMultiGame = instantiateMultiGame();
    }

    public void setCurrentMultiGame(MultiPlayerGame currentMultiGame) {
        this.currentMultiGame = currentMultiGame;
    }

    public int getActiveGamesSize() {
        return activeMultiGames.size();
    }
}
