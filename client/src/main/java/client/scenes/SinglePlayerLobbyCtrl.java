package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;

import java.util.ArrayList;
import java.util.List;

public class SinglePlayerLobbyCtrl {


//    @FXML
//    private CheckBox timeTwister;
    @FXML
    private CheckBox pointBoost;
    @FXML
    private CheckBox detective;
    @FXML
    private CheckBox questionChange;
//    @FXML
//    private CheckBox skipQuestion;
//    @FXML
//    private CheckBox flash;
//    @FXML
//    private CheckBox emergencyCall;
    @FXML
    private RadioButton easy;
    @FXML
    private RadioButton medium;
    @FXML
    private RadioButton hard;
    @FXML
    private RadioButton insane;


    private final MainCtrl mainCtrl;

    ArrayList<CheckBox> checkedJokers = new ArrayList<CheckBox>();
    List<String> checkedStringJokers ;

    @Inject
    public SinglePlayerLobbyCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        checkedStringJokers = new ArrayList<>();
    }

    @FXML
    protected void startGameButtonClick(){
        mainCtrl.goTo("singleGame");
        mainCtrl.setStringJokers(checkedStringJokers);
        resetScreen();
    }

    /**
     * Resets the checkedboxes once the game starts
     */
    private void resetScreen() {
        for(int i = 0; i < checkedStringJokers.size(); i++){
            switch (checkedStringJokers.get(i)){
                case "Additional Points Joker":
                    pointBoost.setSelected(false);
                    break;
                case "Eliminate Option Joker":
                    detective.setSelected(false);
                    break;
                case "Question Change Joker":
                    questionChange.setSelected(false);
                    break;
            }
        }

        checkedStringJokers = new ArrayList<>();
    }

    @FXML
    protected void returnScreen(){
        mainCtrl.goTo("menu");
    }

//    @FXML
//    protected void addEmergencyCall(){
//        addJokerCard(emergencyCall);
//        addJokerCard("EmergencyJoker");
//    }

//    @FXML
//    protected void addTimeTwister(){
//        addJokerCard(timeTwister);
//        addJokerCard("ShortenTimeJoker");
//    }
    public void resetJokers(){
        this.checkedStringJokers = new ArrayList<>();
    }

    @FXML
    protected void addPointBoost(){
        if (pointBoost.isSelected()) {
            System.out.println("is selected");
            addJokerCard(pointBoost);
            addStringJokerCard("Additional Points Joker");
        } else {
            System.out.println("is unselected");
            checkedJokers.remove(pointBoost);
            checkedStringJokers.remove("Additional Points Joker");
        }

    }

    @FXML
    protected void addDetective(){
        // check if it is selected
        if (detective.isSelected()) {
            System.out.println("is selected");
            addJokerCard(detective);
            addStringJokerCard("Eliminate Option Joker");
        } else {
            System.out.println("is unselected");
            checkedJokers.remove(detective);
            checkedStringJokers.remove("Eliminate Option Joker");
        }

    }

    @FXML
    protected void addQuestionChange(){
        if (questionChange.isSelected()) {
            System.out.println("is selected");
            addJokerCard(questionChange);
            addStringJokerCard("Question Change Joker");
        } else {
            System.out.println("is unselected");
            checkedJokers.remove(questionChange);
            checkedStringJokers.remove("Question Change Joker");
        }

    }

//    @FXML
//    protected void addSkipQuestion(){
//        addJokerCard(skipQuestion);
//        addJokerCard("SkipQuestion");
//    }
//
//    @FXML
//    protected void addFlash(){
//        addJokerCard(flash);
//        addJokerCard("Flash");
//    }

    /**
     * This method adds the selected joker cards an ArrayList
     * @param e is the checkbox that was selected
     */
    protected void addJokerCard(CheckBox e){
        if(checkedJokers.size() < 3){
            checkedJokers.add(e);
        }
        if(checkedJokers.size() == 3){
            checkedJokers.remove(0);
            checkedJokers.add(e);
        }
    }

    /**
     * This method adds the String corresponding to the selected joker cards to the ArrayList of Strings
     * @param e String representing the selected joker card
     */
    protected void addStringJokerCard(String e){
        if(checkedStringJokers.size() < 3){
            checkedStringJokers.add(e);
        }
        else if (checkedStringJokers.size() == 3){
            checkedStringJokers.remove(0);
            checkedStringJokers.add(e);
        }
    }

    @FXML
    protected void goToHelp(){
        mainCtrl.goTo("help");
    }


    public void handleEasy(){
        easy.setSelected(true);
        medium.setSelected(false);
        hard.setSelected(false);
        insane.setSelected(false);
        mainCtrl.getSingleplayerStartCountdownScreenCtrl().setDifficulty(30);
    }

    public void handleMedium(){
        easy.setSelected(false);
        medium.setSelected(true);
        hard.setSelected(false);
        insane.setSelected(false);
        mainCtrl.getSingleplayerStartCountdownScreenCtrl().setDifficulty(20);
    }

    public void handleHard(){
        easy.setSelected(false);
        medium.setSelected(false);
        hard.setSelected(true);
        insane.setSelected(false);
        mainCtrl.getSingleplayerStartCountdownScreenCtrl().setDifficulty(12);
    }

    public void handleInsane() {
        easy.setSelected(false);
        medium.setSelected(false);
        hard.setSelected(false);
        insane.setSelected(true);
        mainCtrl.getSingleplayerStartCountdownScreenCtrl().setDifficulty(8);
    }
}
