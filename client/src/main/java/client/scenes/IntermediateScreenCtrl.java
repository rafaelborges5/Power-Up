package client.scenes;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;

public class IntermediateScreenCtrl {
    @FXML
    private Label timeLeftLabel;
    @FXML
    private Label pointsLabel;      // needs to be injected by accessing the points scored by the player.

    private final MainCtrl mainCtrl;

    private static int pointsGained = 0;

    @Inject
    public IntermediateScreenCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Initialises the intermediate between-questions screen with a countdown timer and the points
     * earned by the player after answering the question.
     */
    public void initialiseScene() {
        Timer timerLabel = new Timer();
        timerLabel.scheduleAtFixedRate(new TimerTask() {
            int i = 5;
            @Override
            public void run() {
                Platform.runLater(
                        () -> {
                            timeLeftLabel.setText(String.valueOf(i + 1));
                        }
                );
                i--;
                if(i < 0 ){
                    timerLabel.cancel();
                    Platform.runLater(() -> mainCtrl.checkGameStatus());
                }
            }
        }, 0,1000);
    }

    // set the pointsLabel to the appropriate points depending on the type of question.

    /**
     * Sets the points label to the appropriate number depending on the preceding question.
     */
    public void setPointsLabel() {
        this.pointsLabel.setText(String.valueOf(pointsGained));
        setPointsGained(0); // refresh
    }

    public static int getPointsGained() {
        return pointsGained;
    }

    public static void setPointsGained(int pointsGained) {
        IntermediateScreenCtrl.pointsGained = pointsGained;
    }
}
