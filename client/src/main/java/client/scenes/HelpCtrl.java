package client.scenes;

import javafx.fxml.FXML;

import javax.inject.Inject;


public class HelpCtrl{
    private final MainCtrl mainCtrl;

    @Inject
    public HelpCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }

    @FXML
    protected void goToMenu(){
        mainCtrl.getVisitedScreens().pop(); // removes "help" from the top of the stack
        String previousScreen = mainCtrl.getVisitedScreens().pop();
        mainCtrl.goTo(previousScreen);
    }
}