package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
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
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InsertUsernameMultiplayerCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private final MultiPlayerLobbyCtrl lobby;
    private String storedUsername;
    List<String> listOfPlayer;

    @Inject
    public InsertUsernameMultiplayerCtrl(MainCtrl main, ServerUtils server, MultiPlayerLobbyCtrl lobby) {
        this.mainCtrl=main;
        this.server = server;
        this.lobby = lobby;
    }

    @FXML
    private BorderPane root;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField username;
    @FXML
    private TextField url;
    @FXML
    private Button submitButton;
    @FXML
    private Label alert;
    @FXML
    private Label error;


    /**
     * This method sends the username and url in order to connect to the server and add the player to the right lobby
     * but it is not fully implemented yet.
     */
    public void submit() throws IOException {
        String insertedUsername = username.getText();
        if(insertedUsername.length() > 13) {
            alert.setText("Username too long!");
            return;
        }
        if (insertedUsername.length() == 0|| insertedUsername.contains(" ")) {
            alert.setText("Invalid username");
            return;
        }
        listOfPlayer = server.getCurrentMultiGamePlayers().stream().
                    map(Player::getUsername).collect(Collectors.toList());
        if(listOfPlayer.contains(insertedUsername)) {
            alert.setText("Username already exists!");
            return;
        }
        String insertedUrl = url.getText();
        if(!insertedUrl.endsWith("/")) {
            insertedUrl = insertedUrl + "/";
        }

        if(server.testConnection(insertedUrl)){
            server.setSERVER(insertedUrl);
        }
        else{
            System.out.println("Provided server url is wrong!");
            mainCtrl.goTo("error");
            return;
        }
        Player thisPlayer = new Player(insertedUsername, 0);
        server.sendPlayer(thisPlayer);
        mainCtrl.setLocalPlayer(thisPlayer);
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
        mainCtrl.goTo("multiLobby");
    }

    /**
     * This method prepares the scene in order to respond to the input of the user
     */
    public void prepare() {
        username.setText(storedUsername);
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
    public void initialize() throws FileNotFoundException {
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

    /**
     * This method server to return to the menu
     */
    public void returnToMenu() {
        mainCtrl.goTo("menu");
    }
}
