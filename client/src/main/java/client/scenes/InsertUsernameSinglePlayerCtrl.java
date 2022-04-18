package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class InsertUsernameSinglePlayerCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private String storedUsername;


    @Inject
    public InsertUsernameSinglePlayerCtrl(MainCtrl main) {
        this.mainCtrl=main;
        this.server = mainCtrl.getServer();
    }

    @FXML
    private Label usernameLabel;
    @FXML
    private TextField username;

    @FXML
    private Label error;

    @FXML
    private TextField url;

    @FXML
    private Button submitButton;

    @FXML
    private BorderPane root;


    /**
     * This method sends the username inserted by the user to the createPlayer method in order to create a new
     * Player instance that will be passed to the playSinglePLayerGame
     */
    public void submit() throws IOException {
        String insertedUsername = username.getText();
        if (insertedUsername == null || insertedUsername.length() == 0 || insertedUsername.contains(" ")) {
            error.setText("Invalid username");
            return;
        }
        String serverURL = url.getText();
        if(!serverURL.endsWith("/")) {
            serverURL = serverURL + "/";
        }
        if(server.testConnection(serverURL)){
            server.setSERVER(serverURL);
            Player player = mainCtrl.createPlayer(insertedUsername,mainCtrl.getStringJokers());
            mainCtrl.startSinglePlayerGameCountdown(player);

        }
        else{
            mainCtrl.goTo("error");
            System.out.println("Provided server url is wrong!");
            return;
        }

        String userNameToStore = username.getText();
        FileWriter writer = null;
        try {
            writer = new FileWriter("src/main/resources/username");
        } catch (FileNotFoundException e) {
            try {
                writer = new FileWriter("client/src/main/resources/username");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        assert writer != null;
        writer.write(userNameToStore);
        writer.close();
    }

    /**
     * This method prepares the scene in order to respond to the input of the user
     */
    public void prepare() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                username.setText(storedUsername);
            }
        });
        root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                ev.consume();
                try {
                    submit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * This method will retrieve the username that is stored in the local file, which is the last username this
     * precise user has used
     * @throws FileNotFoundException in case the file storing the username is not found
     */
    public void initialize() throws FileNotFoundException{
        Scanner usernameScanner = null;
        try {
            usernameScanner = new Scanner(new File("src/main/resources/username"));
        } catch (FileNotFoundException e) {
            try {
                usernameScanner = new Scanner(new File("client/src/main/resources/username"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if(usernameScanner.hasNext())
            this.storedUsername = usernameScanner.next();

        usernameScanner.close();
    }

    public void returnToLobby(){
        mainCtrl.goTo("singleLobby");
    }
}
