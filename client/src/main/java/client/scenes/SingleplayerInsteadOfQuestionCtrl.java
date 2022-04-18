package client.scenes;

import client.utils.ServerUtils;
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

import javax.inject.Inject;
import java.net.URL;
import java.util.*;

public class SingleplayerInsteadOfQuestionCtrl implements Initializable {

    private ServerUtils server;
    @FXML
    private Button exit;

    @FXML
    private HBox emojiBar;
    @FXML
    private Label jokerAlertMessage;

    @FXML
    private Label ReactionName;

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
    private Text activity1ratio;

    @FXML
    private Text activity2ratio;

    @FXML
    private Text activity3ratio;

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
    public Label time;

    private final int timeBarWidth = 950;

    @FXML
    private ProgressBar progressBar;

    private final MainCtrl mainCtrl;
    private InsteadOfQuestion questionObject;

    private IntermediateScreenCtrl intermediateScreenCtrl;
    private static int pointsGained;   //

    //TODO : set pointsGained to questionObject.getAvailablePoints() when answer validation is done.


    @Inject
    public SingleplayerInsteadOfQuestionCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = mainCtrl.getServer();
    }

    private String pluralOrSingular(int x) {
        if (x==1) {
            return "";
        } else {
            return "s";
        }
    }
    /**
     * /**
     *      This method initialises all the JFX fields with
     *      attributes of the Question and Player Classes.
     */
    public void initialiseSinglePlayerInsteadOfQuestion() {
        resetScreen();
        switchButtons(false);
        Game currentGame = mainCtrl.getGame();
        InsteadOfQuestion q =
                (InsteadOfQuestion) currentGame.
                        getQuestions().
                        get(currentGame.getCurrentQuestionNumber());
        questionObject = q;
        this.setEmojiBarVisible(currentGame);
        Player player = mainCtrl.getLocalPlayer();
        score.setText("Score: " + player.getCurrentScore());
        Activity activity = q.getActivity();
        question.setText("Instead of " + activity.getTitle() + ", you could:");
        if(activity.getTitle().length() >= 53) {
            question.setStyle("-fx-font-size: 13;");
        }
        ArrayList<Activity> options = q.getOptions();
        Activity correctAnswer = q.getCorrectAnswer();

        List<Activity> optionsCopy = new ArrayList<>();
        for(Activity option : options){
            optionsCopy.add(option);
        }

        int ratio1 = q.getCorrectRatio(correctAnswer);
        int ratio2 = q.getWrongRatio((options.get(1)));
        int ratio3 = q.getWrongRatio((options.get(2)));
        String option1ratio = ratio1 + " time" + pluralOrSingular(ratio1);
        String option2ratio = ratio2 + " time" + pluralOrSingular(ratio2);
        String option3ratio = ratio3 + " time" + pluralOrSingular(ratio3);

        // In this loop we are making sure that randomly assigned wrongRatio
        // is not accidentally the correct one.
        // We are assigning it randomly until both of them are not equal to the correctRatio

        while(ratio2 == (q.getCorrectRatio(options.get(1))) ||
                ratio3 == (q.getCorrectRatio(options.get(2)))){
            option2ratio = q.getWrongRatio((options.get(1))) + " time" +
                    pluralOrSingular(q.getWrongRatio((options.get(1))));
            option3ratio = q.getWrongRatio((options.get(2))) + " time" +
                    pluralOrSingular(q.getWrongRatio((options.get(2))));
        }

        Map<String, String> optionsWithRatios = new HashMap<>();
        optionsWithRatios.put(optionsCopy.get(0).getTitle(), option1ratio);
        optionsWithRatios.put(optionsCopy.get(1).getTitle(), option2ratio);
        optionsWithRatios.put(optionsCopy.get(2).getTitle(), option3ratio);

        List<String> answers = new ArrayList<>();
        answers.add(option1ratio);
        answers.add(option2ratio);
        answers.add(option3ratio);


        Collections.shuffle(options);
//        Collections.shuffle(answers);

        question1Text.setText(options.get(0).getTitle() + " " + optionsWithRatios.get(options.get(0).getTitle()));
        question2Text.setText(options.get(1).getTitle() + " " + optionsWithRatios.get(options.get(1).getTitle()));
        question3Text.setText(options.get(2).getTitle() + " " + optionsWithRatios.get(options.get(2).getTitle()));

//        activity1ratio.setText(optionsWithRatios.get(options.get(0).getTitle()));
//        activity2ratio.setText(optionsWithRatios.get(options.get(1).getTitle()));
//        activity3ratio.setText(optionsWithRatios.get(options.get(2).getTitle()));

        setQuestionNumber(currentGame.getCurrentQuestionNumber());

        List<JokerCard> jokerCards = player.getJokerCards();
        initialiseActivityImages(options);
        setJokers(jokerCards);
        jokerAlertMessage.setText("");
        jokerAlertMessage.setText("");
    }

    /**
     *
     */
    public void initialiseAfterJoker(){
        if(questionObject.getOptions().indexOf(questionObject.getCorrectAnswer()) != 0)
        {
            question1Text.setText("Wrong option!");

            option1Image.setImage(null);
        }
        else
        {
            question2Text.setText("Wrong option!");

            option2Image.setImage(null);
        }

    }


    private void resetScreen() {
        option1.setStyle("-fx-background-color: #8ECAE6");
        option2.setStyle("-fx-background-color: #8ECAE6");
        option3.setStyle("-fx-background-color: #8ECAE6");

    }

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
        if(questionObject.getOptions().indexOf(questionObject.getCorrectAnswer()) == 0){
            changeButtonColours(option1, "green");
            handleCorrect();
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
        if(questionObject.getOptions().indexOf(questionObject.getCorrectAnswer()) == 1){
            changeButtonColours(option2, "green");
            handleCorrect();
        } else {
            changeButtonColours(option2, "red");
            handleWrong();
        }
        switchButtons(true);
    }

    /**
     * Handles the clicks on button with option 3
     */
    public void option3Handler(){
        if(questionObject.getOptions().indexOf(questionObject.getCorrectAnswer()) == 2){
            changeButtonColours(option3, "green");
            handleCorrect();
        } else {
            changeButtonColours(option3, "red");
            handleWrong();
        }
        switchButtons(true);
    }

    /**
     * This method will handle when the user click the correct option. For the moment it is increasing the points of the
     * player and printing out correct
     */
    void handleCorrect() {
        // get the time left
        Game game = mainCtrl.getGame();
        questionObject = (InsteadOfQuestion) game.getQuestions().get(game.getCurrentQuestionNumber());
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
        if (questionObject.getOptions().indexOf(questionObject.getCorrectAnswer()) == 0) {
            changeButtonColours(option1, "green");
        } else if(questionObject.getOptions().indexOf(questionObject.getCorrectAnswer()) == 1) {
            changeButtonColours(option2, "green");
        } else {
            changeButtonColours(option3, "green");
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
        else{
            jokerAlertMessage.setText("This joker cannot be used in this type of question!");
        }
        }

    public boolean canUseJoker(String name){
//        if(name.equals("EliminateOptionJoker"))
//            return false;
        return true;
    }

    private void initialiseActivityImages(List<Activity> activityList) {
        String serverString = server.getServer();

        option1Image.setImage(new Image(serverString + activityList.get(0).getImage_path()));
        option2Image.setImage(new Image(serverString + activityList.get(1).getImage_path()));
        option3Image.setImage(new Image(serverString + activityList.get(2).getImage_path()));
    }

    @FXML
    protected void goToMenu() {
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
            allowedTime  = mainCtrl.getGame().getQuestions().get(mainCtrl.getGame().getCurrentQuestionNumber())
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

    public int getPointsGained() {
        return pointsGained;
    }

    public void setPointsGained(int pointsGained) {
        this.pointsGained = pointsGained;
    }

    public void setQuestionNumber(int i) {
        double progress = (double) i / 20.0;
        progressBar.setProgress(progress);
    }

    /**
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

    public void hideEmoji() {
        emojiBar.setVisible(false);
    }

    @FXML
    public void exit() {
        mainCtrl.setExitedGame(true);
        mainCtrl.goTo("menu");
    }
    private void setEmojiBarVisible(Game currentGame) {
        if(currentGame instanceof MultiPlayerGame){
            emojiBar.setVisible(true);
            Platform.runLater(()->{
                reaction.setImage(null);;
                ReactionName.setText("");});
        }
        else{
            emojiBar.setVisible(false);
        }
    }

    public void initialisejokerAlert(JokerAlert jokerAlert) {
        jokerAlertMessage.setText(jokerAlert.getSenderUsername()+" used "+jokerAlert.getJokerType());
    }

}
