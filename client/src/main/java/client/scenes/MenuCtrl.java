package client.scenes;

import client.MyFXML;
import client.MyModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.google.inject.Guice.createInjector;


public class MenuCtrl implements Initializable {

    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @Inject
    public MenuCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        System.out.println(this.singleIcon == null);
    }

    @FXML
    private Button credits;

    @FXML
    private Button admin;

    @FXML
    private Button exit;

    @FXML
    private ImageView multiIcon;

    @FXML
    private ImageView kachow;

    @FXML
    private Button multiPlayer;

    public void setSingleIcon(ImageView image) {
        this.singleIcon = image;
    }

    @FXML
    private ImageView singleIcon;

    @FXML
    private Button singlePlayer;

    @FXML
    void exitButton(ActionEvent event) {
        mainCtrl.closeStage();
    }

    @FXML
    protected void goToMultiPlayerLobby(){
        mainCtrl.goTo("insertInfoMultiPlayer");
    }

    @FXML
    protected void goToCredits(){
        mainCtrl.goTo("credits");
    }

    @FXML
    protected void goToSinglePlayerLobby() throws IOException {
        mainCtrl.goTo("singleLobby");
    }

    @FXML
    protected void goToHelp(){
        mainCtrl.goTo("help");
    }


    @FXML
    public void goToAdmin() {
        mainCtrl.goTo("admin");
    }

    public void goToLeaderboard(ActionEvent actionEvent) {
        mainCtrl.goTo("SinglePlayerLeaderboard");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singleIcon.setImage(new Image(MainCtrl.class.getResource("/pictures/single_player.png").toString()));
        multiIcon.setImage(new Image(MainCtrl.class.getResource("/pictures/multiplayer.png").toString()));
        kachow.setImage(new Image(MainCtrl.class.getResource("/pictures/lightning_picture.jpg").toString()));

    }
}