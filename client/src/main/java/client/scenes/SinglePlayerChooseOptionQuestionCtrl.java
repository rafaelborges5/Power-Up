package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.*;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class SinglePlayerChooseOptionQuestionCtrl implements Initializable {

    private ServerUtils server;
    @FXML
    private Button exit;

    @FXML
    private HBox emojiBar;

    @FXML
    private Label ReactionName;

    @FXML
    private Label jokerAlertMessage;

    @FXML
    private ImageView anger;
    @FXML
    private ImageView crying;

    @FXML
    private ImageView devil;
    @FXML
    private ImageView inLove;
    @FXML
    private ImageView reaction;
    @FXML
    private ImageView smiling;

    @FXML
    private ImageView thinking;

    @FXML
    private Text question1Text;
    @FXML
    private Text question2Text;
    @FXML
    private Text question3Text;

    @FXML
    private Button joker1;

    @FXML
    private Button joker2;

    @FXML
    private Button joker3;

    @FXML
    private Button option1;

    @FXML
    private ImageView option1Image;

    @FXML
    private Button option2;

    @FXML
    private ImageView option2Image;

    @FXML
    private Button option3;

    @FXML
    private ImageView option3Image;

    @FXML
    private Label question;

    @FXML
    private Label score;

    @FXML
    private ProgressBar progressBar;

    @FXML
    public Label time;


    private final MainCtrl mainCtrl;
    private MostEnergyQuestion questionObject; //the object that is being displayed

    private static int pointsGained;    // points gained from this question.
    List<Activity> activityList=null;

    @Inject
    public SinglePlayerChooseOptionQuestionCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = mainCtrl.getServer();
    }

    /**
     * This method initialises all the JFX fields with attributes of the Question and Player Classes.
     * Goes to the intermediate screen after X seconds where X is the maximum allowed time.
     */
    public void initialiseMostEnergyQuestion() {
        resetScreen();
        switchButtons(false);
        Game currentGame = mainCtrl.getGame();
        this.setEmojiBarVisible(currentGame);
        MostEnergyQuestion q = (MostEnergyQuestion) currentGame.getQuestions().
                get(currentGame.getCurrentQuestionNumber());
        questionObject = q;
        Player player = mainCtrl.getLocalPlayer();
        score.setText("Score: " + player.getCurrentScore());
        List<Activity> actList = new ArrayList<>(q.getOtherActivities());
        actList.add(q.getActivity());
        activityList = actList;
        Collections.shuffle(actList);
        question1Text.setText(actList.get(0).getTitle());
        question2Text.setText(actList.get(1).getTitle());
        if(actList.size()==3) {
            question3Text.setText(actList.get(2).getTitle());
        }
        else{
            question3Text.setText("Wrong option");
        }

        question.setText("What requires more energy?");
        initialiseActivityImages(actList);

        setQuestionNumber(currentGame.getCurrentQuestionNumber());

        List<JokerCard> jokerList = player.getJokerCards();
        jokerAlertMessage.setText("");
        this.setJokers(jokerList);
    }


    private void resetScreen() {
        option1.setStyle("-fx-background-color: #8ECAE6");
        option2.setStyle("-fx-background-color: #8ECAE6");
        option3.setStyle("-fx-background-color: #8ECAE6");
    }

    /**
     * This method initialises the Image views with the corresponding image of the activities
     * @param activityList List of instances of the Activity Class
     */
    /**
     * This method will switch the buttons on or off according to the boolean passed. True means off
     * @param onOff the boolean for which to set the setDisable property
     */
    void switchButtons(boolean onOff) {
        option1.setDisable(onOff);
        option2.setDisable(onOff);
        option3.setDisable(onOff);
        joker1.setDisable(onOff);
        joker2.setDisable(onOff);
        joker3.setDisable(onOff);
    }

    private void initialiseActivityImages(List<Activity> activityList) {
       String  serverString = server.getServer();;

        option1Image.setImage(new Image(serverString + activityList.get(0).getImage_path()));
        option2Image.setImage(new Image(serverString + activityList.get(1).getImage_path()));
        if(activityList.size()==3) {
            option3Image.setImage(new Image(serverString + activityList.get(2).getImage_path()));
        }
        else{
            option3Image.setImage(null);
        }
    }

    /**
     * Changes a button's background colour to the colour specified.
     * @param button Button whose colour needs to be changed.
     * @param colour Colour to change to.
     */
    private void changeButtonColours(Button button, String colour) {
        if (colour.equals("green")) {
            button.setStyle("-fx-background-color: green");
        } else {
            button.setStyle("-fx-background-color: red");
        }
    }


    /**
     * Handles the clicks on button with option 1
     */
    public void option1Handler() {
        if(activityList.indexOf(generateExpensiveActivity()) == 0) {
            handleCorrect();
            changeButtonColours(option1, "green");
        } else {
            changeButtonColours(option1, "red");
            handleWrong();
        }
        switchButtons(true);
    }

    /**
     * Handles the clicks on button with option 2
     */
    public void option2Handler() {
        if(activityList.indexOf(generateExpensiveActivity()) == 1) {
            handleCorrect();
            changeButtonColours(option2, "green");
        } else {
            changeButtonColours(option2, "red");
            handleWrong();
        }
        switchButtons(true);
    }

    /**
     * Handles the clicks on button with option 3
     */
    public void option3Handler() {
        if(activityList.indexOf(generateExpensiveActivity()) == 2) {
            handleCorrect();
            changeButtonColours(option3, "green");
        } else {
            changeButtonColours(option3, "red");
            handleWrong();
        }
        switchButtons(true);
    }


    /**
     * This method will return the most expensive activity object from the questionObject stored in this class
     * @return the Activity that is the correct answer of this question
     */
    public Activity generateExpensiveActivity() {

        Activity correct = activityList.get(0);
        for(Activity a : activityList) {
            if(a.getConsumption_in_wh() > correct.getConsumption_in_wh()) {
                correct = a;
            }
        }
        return correct;
    }

    @FXML
    void exit() {
        mainCtrl.setExitedGame(true);
        mainCtrl.goTo("menu");
    }

    /**
     * This method sets the numerical representation of the timer and also changes the colour of the label to represent
     * how far the player is in the question
     * @param i is the time left for the question
     */
    public void setTime(int i) {
        time.setText("Time Left: " + i + " seconds");
        int colourChange1;
        int colourChange2;
        int colourChange3;
        int allowedTime ;
        if(mainCtrl.getGame() instanceof SinglePlayerGame){
            allowedTime = mainCtrl.getGame().getQuestions().get(mainCtrl.getGame().getCurrentQuestionNumber())
                    .getAllowedTime();
            colourChange1 = (int) ( allowedTime * 0.75);
            colourChange2 = (int) ( allowedTime * 0.5);
            colourChange3 = (int) ( allowedTime * 0.25);

        }
        else{
            allowedTime=20;
            colourChange1 = 15;
            colourChange2 = 10;
            colourChange3 = 5;
        }
        if(i <= allowedTime && i >= colourChange1){
            time.setStyle("-fx-background-color: #00FF00");
        }
        if(i < colourChange1 && i >= colourChange2){
            time.setStyle("-fx-background-color: #FFFF00");
        }
        if(i < colourChange2 && i >= colourChange3){
            time.setStyle("-fx-background-color: #FFA500");
        }
        if(i < colourChange3){
            time.setStyle("-fx-background-color: #FF0000");
        }
    }

    /**
     * This method maps the player's jokers to their corresponding buttons
     * @param jokerList List of JokerCard instances representing the player's jokers
     */
    public void setJokers(List<JokerCard> jokerList){
        Button[] buttonArray ={ joker1,joker2,joker3};
        for(int i=0;i<buttonArray.length;i++){
            Button current = buttonArray[i];
            if(i<=jokerList.size()-1){
                current.setText(jokerList.get(i).getName());
            }
            else{
                current.setText("Unavailable Joker");
                current.setDisable(true);
            }
        }
    }

    /**
     * This method will handle when the user click the correct option. For the moment it is increasing the points of the
     * player and printing out correct
     */
    void handleCorrect() {
        Game game = mainCtrl.getGame();
        questionObject = (MostEnergyQuestion) game.getQuestions().get(game.getCurrentQuestionNumber());
        Player p = null;
        if(game instanceof SinglePlayerGame) {
            p = ((SinglePlayerGame) game).getPlayer();
            int timeAfterQuestionStart = questionObject.getAllowedTime() - MainCtrl.getTimeLeft();
            double quotient = (double) timeAfterQuestionStart / (double) questionObject.getAllowedTime();
            int points = (int) ((1 - 0.5 * quotient) * questionObject.getAvailablePoints());
            p.setCurrentScore(p.getCurrentScore() + points);
            IntermediateScreenCtrl.setPointsGained(points);
        } else {
            MultiPlayerGame m = (MultiPlayerGame) game;
            int tl = 0;
            for(int i = 0; i < m.getPlayers().size(); i++) {
                Player localPlayer = mainCtrl.getLocalPlayer();
                tl = localPlayer.getTimeLeft();
                Player toSearch = m.getPlayers().get(i);
                if(toSearch.getUsername().equals(localPlayer.getUsername())) {
                    p = m.getPlayers().get(i);
                }
            }
            // we now have player
            int timeAfterQuestionStart = questionObject.getAllowedTime() - tl;
            double quotient = (double) timeAfterQuestionStart / (double) questionObject.getAllowedTime();
            int points = (int) ((1 - 0.5 * quotient) * questionObject.getAvailablePoints());
            p.setCurrentScore(p.getCurrentScore() + points);
        }

        mainCtrl.getLocalPlayer().setCurrentScore(p.getCurrentScore());
        if(game instanceof MultiPlayerGame) {
            server.updatePlayerScore(new Player(p.getUsername(), p.getCurrentScore()), mainCtrl.getGameId());
        }

    }

    /**
     * This method will handle when the user clicks the wrong option. For the moment it is only printing wrong on the
     * console
     */
    void handleWrong() {
        IntermediateScreenCtrl.setPointsGained(0);
        if (activityList.indexOf(generateExpensiveActivity()) == 0) {
            changeButtonColours(option1, "green");
        } else if(activityList.indexOf(generateExpensiveActivity()) == 1) {
            changeButtonColours(option2, "green");
        } else {
            changeButtonColours(option3, "green");
        }
    }
    @FXML
    void handleJokerButton1() {
        if(canUseJoker(joker1.getText())) {
            jokerAlertMessage.setText("");
            mainCtrl.setUsedJoker(joker1.getText());
            mainCtrl.handleJoker();
            joker1.setDisable(true);

        }
        else{
            jokerAlertMessage.setText("This joker cannot be used in this type of question!");
        }
    }
    @FXML
    void handleJokerButton2() {
        if(canUseJoker(joker2.getText())) {
            jokerAlertMessage.setText("");
            mainCtrl.setUsedJoker(joker2.getText());
            mainCtrl.handleJoker();
            joker2.setDisable(true);
        }
        else{
            jokerAlertMessage.setText("This joker cannot be used in this type of question!");
        }
    }
    @FXML
    void handleJokerButton3() {
        if (canUseJoker(joker3.getText())) {
            jokerAlertMessage.setText("");
            mainCtrl.setUsedJoker(joker3.getText());
            mainCtrl.handleJoker();
            joker3.setDisable(true);
        }
        else {
             jokerAlertMessage.setText("This joker cannot be used in this type of question!");
        }
    }
    public boolean canUseJoker(String name){
//        if(name.equals("EliminateOptionJoker"))
//            return false;
        return true;
    }


    public void setQuestionNumber(int i) {
        double progress = (double) i / 20.0;
        progressBar.setProgress(progress);
    }

    public int getPointsGained() {
        return pointsGained;
    }

    public void setPointsGained(int pointsGained) {
        this.pointsGained = pointsGained;
    }
    /**
     * This method send the Emoji to the other clients through WebSockets.
     * This method send the Emoji to the other clients through WebSockets.
     * @param e Instance of Emoji Class that contains an emoji with the Player's username and it's image path.
     */
    public void sendEmoji(Emoji e){
        server.send("/app/emojis/"+mainCtrl.getGameId(),e);
    }
    /**
     * This  method creates an Emoji and passes it to the sendEmoji() method
     * @param event Event that occurs when an image view for Emoji is pressed.
     */
    public void getEmoji(Event event){
        Emoji e =  new Emoji(mainCtrl.getLocalPlayer().getUsername(),((ImageView)event.getSource()).
                getImage().getUrl());
        sendEmoji(e);
    }

    /**
     *
     * @param absolutePath
     * @return
     */

    public String getLocalPath(String absolutePath){
        String[] result = absolutePath.split("/");
        return "/"+ result[result.length-2] + "/" + result[result.length-1];
    }


    /**
     * This method initialises the Scene with the last Emoji that was sent through the WebSocket.
     * @param e Instance of Emoji Class( sent through the WebSocket for Emoji Class)
     */
    public void initialiseEmoji(Emoji e) {
        ReactionName.setText(e.getSender());
        String localPath = MainCtrl.class.getResource(getLocalPath(e.getEmojiPath())).toString();
        reaction.setImage(new Image(localPath));
        ScaleTransition scale = new ScaleTransition(Duration.millis(50),reaction);
        scale.setToX(1);
        scale.setToY(1);
        scale.setFromX(0.75);
        scale.setFromY(0.75);
        scale.play();

    }
    /**
     * This method initialises the Emojis images because they are not rendered directly for Windows users.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        smiling.setImage(new Image(MainCtrl.class.getResource("/pictures/smilingTeeth.png").toString()));
        anger.setImage(new Image(MainCtrl.class.getResource("/pictures/anger.png").toString()));
        devil.setImage(new Image(MainCtrl.class.getResource("/pictures/devil.png").toString()));
        inLove.setImage(new Image(MainCtrl.class.getResource("/pictures/in-love.png").toString()));
        thinking.setImage(new Image(MainCtrl.class.getResource("/pictures/thinking.png").toString()));
        crying.setImage(new Image(MainCtrl.class.getResource("/pictures/crying.png").toString()));

    }

    private void setEmojiBarVisible(Game currentGame) {
        if(currentGame instanceof MultiPlayerGame){
            emojiBar.setVisible(true);
            Platform.runLater(()->{
                reaction.setImage(null);
                ReactionName.setText("");});
        }
        else{
            emojiBar.setVisible(false);
        }
    }

    public void hideEmoji() {
        emojiBar.setVisible(false);
    }

    public void initialisejokerAlert(JokerAlert jokerAlert) {
        jokerAlertMessage.setText(jokerAlert.getSenderUsername()+" used "+jokerAlert.getJokerType());
    }
}

