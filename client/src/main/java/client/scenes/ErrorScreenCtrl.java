package client.scenes;

import javafx.fxml.FXML;

import javax.inject.Inject;


public class ErrorScreenCtrl{
    private final MainCtrl mainCtrl;

    @Inject
    public ErrorScreenCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }

    @FXML
    protected void goToMenu(){
        mainCtrl.getVisitedScreens().pop(); // removes "ErrorScreen" from the top of the stack
        String previousScreen = mainCtrl.getVisitedScreens().pop();
        if(previousScreen.equals("admin") || previousScreen.equals("SinglePlayerLeaderboard")) {
            mainCtrl.goTo("menu");
            return;
        }
        mainCtrl.goTo(previousScreen);
    }
}