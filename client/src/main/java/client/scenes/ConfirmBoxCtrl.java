package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.inject.Inject;

public class ConfirmBoxCtrl {

    private MainCtrl mainCtrl;

    @Inject
    public ConfirmBoxCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }

    @FXML
    private Button noButton;

    @FXML
    private Button yesButton;

    public void handleYes() {
        setAnswer(true);
    }

    public void handleNo() {
        setAnswer(false);
    }

    /**
     * Send command to the mainCtrl with what to do with the game
     * @param answer whether to shut the game down or to ignore
     */
    public void setAnswer(boolean answer) {
        System.out.println(answer);
        mainCtrl.setAnswer(answer);
    }



}
