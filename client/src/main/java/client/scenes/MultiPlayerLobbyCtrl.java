package client.scenes;

import client.utils.ServerUtils;
import commons.MultiPlayerGame;
import commons.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class MultiPlayerLobbyCtrl {

    @FXML
    private Label alert;
    @FXML
    private Label numberOfPlayersLabel;
    @FXML
    private TextArea userNames;
    @FXML
    private Button startGame;

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    List<String> playerUsernames;
    private MultiPlayerGame game;
    private int gameId;
    private Player thisPlayer;
    private boolean starting = false;

    @Inject
    public MultiPlayerLobbyCtrl(MainCtrl mainCtrl, ServerUtils server){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * This method starts the timer for the game to start, sets the needed fields with some info for the user, sends the
     * information that the game is about to start through he websocket so other users can also execute this method.
     */
    @FXML
    protected void startGameButton(){
        if(starting) return;
        starting = true;
        server.send("/app/startGame", true);
        Timer timer1 = new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            int i = 6;
            public void run() {
                Platform.runLater(() -> {
                    if(i >= 1) alert.setText("Game Starts in " + i + " seconds");
                });

                if (i == 1) {
                    Platform.runLater(() -> alert.setText("Game Starting!"));
                }

                if(i < 0){
                    timer1.cancel();
                    mainCtrl.startMultiPlayerGame();
                    Platform.runLater(mainCtrl::playMultiPLayerGame); //link with playMultiPlayer
                }

                i--;
            }
        }, 0, 1000);
    }

    /**
     * This method is for when the player clicks the return button. This will delete the player from the game by
     * sending it through the websocket with sendPlayer()
     */
    @FXML
    protected void returnScreen(){
        server.sendPlayer(new Player(thisPlayer.getUsername(), thisPlayer.getCurrentScore()));
        thisPlayer = null;
        mainCtrl.goTo("insertInfoMultiPlayer");
    }

    /**
     * This method will prepare the Lobby for the user. It initializes the field game with the current ongoing
     * MultiPlayerGame, it fetches the list of current players and refreshes the UI list. It also sets eventListeners
     * for when a player is deleted/added to the MultiPlayerGame
     */
    public void prepare() {
        userNames.setEditable(false);
        starting = false;
        game = server.getCurrentMultiplayerGame();
        this.gameId = server.getCurrentMultiplayerGameId();
        playerUsernames = game.getPlayers().stream().map(Player::getUsername).collect(Collectors.toList());
        refresh();
        server.registerForNewPlayers("/topic/updateLobby", p -> {
            List<Player> currentPlayers = server.getCurrentMultiGamePlayers();
            playerUsernames = currentPlayers.stream().map(Player::getUsername).
                    collect(Collectors.toList());
            this.game.setPlayers((ArrayList<Player>) currentPlayers);
            Platform.runLater(() -> refresh());
        });
        server.registerForGameStart("/topic/startGame", b -> startGameButton());
        mainCtrl.setGame(game);
        mainCtrl.setGameId(gameId);
    }


    /**
     * This method server to refresh the list of players. It will also do some checking in order to disable/enable
     * the readyButton
     */
    public void refresh() {
        if(playerUsernames.size() < 2) {
            startGame.setDisable(true);
            alert.setText("There aren't enough players!");
        } else {
            startGame.setDisable(false);
            alert.setText("");
        }
        numberOfPlayersLabel.setText(playerUsernames.size() + " Players");
        userNames.setText(MakeList(playerUsernames));
    }

    /**
     * This method will return a string-form of a list of users
     * @param currentUsernames the list of users
     * @return the string-form of the list
     */
    private String MakeList(List<String> currentUsernames) {
        String currentUsers = "";
        for(int i = 0; i < playerUsernames.size(); i++){
            currentUsers = currentUsers + playerUsernames.get(i) + "\n";
        }
        return currentUsers;
    }

    /**
     * This method server to set the value of the own player
     * @param thisPlayer
     */
    public void setThisPlayer(Player thisPlayer) {
        this.thisPlayer = thisPlayer;
    }

    /**
     * This method is to be performed when the user clicks on the close button instead of return
     */
    public void tearDown() {
        if(thisPlayer != null) {
            server.sendPlayer(new Player(thisPlayer.getUsername(), thisPlayer.getCurrentScore()));
        }
    }

    @FXML
    protected void goToHelp(){
        mainCtrl.goTo("help");
    }

}
