package client.scenes;

import javafx.fxml.FXML;

import javax.inject.Inject;


public class CreditsCtrl {
    private final MainCtrl mainCtrl;

    @Inject
    public CreditsCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @FXML
    protected void returnMenu(){
        mainCtrl.goTo("menu");
    }

    @FXML
    protected void goToHelp(){
        mainCtrl.goTo("help");
    }
}
