package client.scenes;

import commons.MultiPlayerGame;
import commons.Player;
import commons.SinglePlayerGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.util.*;

public class MultiplayerIntermediateScreenCtrl {
    @FXML
    private Label name1;

    @FXML
    private Label name2;

    @FXML
    private Label name3;

    @FXML
    private Label name4;

    @FXML
    private Label name5;

    @FXML
    private Label name6;

    @FXML
    private Label name7;

    @FXML
    private Label name8;

    @FXML
    private Label name9;

    @FXML
    private Label name10;

    @FXML
    private Label playerName;

    @FXML
    private Label playerScore;

    @FXML
    private Label score1;

    @FXML
    private Label score2;

    @FXML
    private Label score3;

    @FXML
    private Label score4;

    @FXML
    private Label score5;

    @FXML
    private Label score6;

    @FXML
    private Label score7;

    @FXML
    private Label score8;

    @FXML
    private Label score9;

    @FXML
    private Label score10;

    @FXML
    private Text countdown;
    private MainCtrl mainCtrl;

    int i=5;

    @Inject
    public MultiplayerIntermediateScreenCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Starts the countdown for the next question
     */
    public void startCountdown() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    countdown.setText("Game Continues in " + i + " Seconds");
                });
                i--;
                if(i <= 0) {
                    timer.cancel();
                    i = 5;
                    Platform.runLater(() -> mainCtrl.checkGameStatus());
                }
            }
        }, 0, 1000);

    }

    /**
     *
     * @param additionalTime
     */
    public void setI(int additionalTime) {
        this.i = 5+additionalTime;
    }


    /**
     * Retrieves the game and initialises the leaderboard by displaying the top ten players in descending order.
     */
    public void initialiseLeaderboard() {
        // get list of players from server util
        List<Player> players = ((MultiPlayerGame) mainCtrl.getGame()).getPlayers();

        int numOfPlayers = players.size();


        // if there are fewer than 10 players, we only want to set the first N rows in the leaderboard
        int min = Math.min(10, numOfPlayers);


        // sort leaderboard in descending order from 1 to 10

        players.sort((o1, o2) -> o2.getCurrentScore() - o1.getCurrentScore());

        // truncate list to only get the first 10
        players = players.subList(0, min);


        Label[] names = {name1, name2, name3, name4, name5, name6, name7, name8, name9, name10};
        Label[] scores = {score1, score2, score3, score4, score5, score6, score7, score8, score9, score10};

        // set name and score labels to their corresponding values retrieved from the server.
        for (int i = 0; i < min; i++) {
            names[i].setText(players.get(i).getUsername());
            scores[i].setText(String.valueOf(players.get(i).getCurrentScore()));
        }

        // check if there is a game stored.
        if (mainCtrl.getGame() != null) {
            Player localPlayer;
            if(mainCtrl.getGame() instanceof SinglePlayerGame) {
                localPlayer = ((SinglePlayerGame) mainCtrl.getGame()).getPlayer();
            } else {
                localPlayer = mainCtrl.getLocalPlayer();
            }

            playerName.setText(localPlayer.getUsername());
            playerScore.setText(String.valueOf(localPlayer.getCurrentScore()));
        }


    }
}
