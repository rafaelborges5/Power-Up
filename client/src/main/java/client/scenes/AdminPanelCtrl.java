package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Injector;
import commons.Activity;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.inject.Guice.createInjector;

public class AdminPanelCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    public List<Activity> listOfActivities;
    public List<Activity> allActivities;
    private int currentIndex = 0;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @Inject
    public AdminPanelCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.server = serverUtils;
    }

    @FXML
    private Button create;

    @FXML
    private Label activity1;

    @FXML
    private Label activity2;

    @FXML
    private Label activity3;

    @FXML
    private Label activity4;

    @FXML
    private Label activity5;

    @FXML
    private Label activity6;

    @FXML
    private Label activity7;

    @FXML
    private Label activity8;

    @FXML
    private Label activity9;

    @FXML
    private Label activity10;

    @FXML
    private Button next;

    @FXML
    private Button previous;

    @FXML
    private Button returnMenu;

    @FXML
    public TextField searchBox;

    @FXML
    public void goToMenu() {
        this.mainCtrl.goTo("menu");
    }

    public void nextPage() {
        instantiateActivities(true, false);
    }

    public void previousPage() {
        instantiateActivities(false, false);
    }

    public void editActivity(String id) {
        mainCtrl.goToEditActivity(this.server.getActivityById(id));
    }

    public void setListOfActivities(List<Activity> listOfActivities) {
        this.listOfActivities = listOfActivities;
    }

    /**
     * This method will get the set of Activities to display, and it will map their id to the corresponding label in
     * the scene
     * @param direction which the user requested, it is either the following activities or the previous activities
     * @param firstTime regarding if it is the firstTime this admin panel will be instantiating activities
     */
    public void instantiateActivities(boolean direction, boolean firstTime) {
        List<Activity> filteredList = listOfActivities.stream().filter(a -> a.getId().contains(searchBox.
                        getCharacters())).collect(Collectors.toList());
        List<String> list;
        if(direction) {
            if(!firstTime) currentIndex += 10;
            previous.setDisable(firstTime);
            if(currentIndex + 10 >= (filteredList.size() - 1)) {
                List<Activity> temp = filteredList.subList(currentIndex, filteredList.size());
                list = temp.stream().map(Activity::getId).collect(Collectors.toList());
                while(list.size() < 10) list.add("");
                next.setDisable(true);
            } else {
                List<Activity> temp = filteredList.subList(currentIndex, currentIndex + 10);
                list = temp.stream().map(Activity::getId).collect(Collectors.toList());
            }
        } else {
            next.setDisable(false);
            List<Activity> temp = filteredList.subList(currentIndex - 10, currentIndex);
            list = temp.stream().map(Activity::getId).collect(Collectors.toList());
            currentIndex -= 10;
            if(currentIndex == 0) previous.setDisable(true);
        }
        Platform.runLater(() -> mapActivities(list));
    }

    /**
     * This method will map activity ids to their corresponding label
     * @param smallList
     */
    public void mapActivities(List<String> smallList) {
        activity1.setText(smallList.get(0));
        activity2.setText(smallList.get(1));
        activity3.setText(smallList.get(2));
        activity4.setText(smallList.get(3));
        activity5.setText(smallList.get(4));
        activity6.setText(smallList.get(5));
        activity7.setText(smallList.get(6));
        activity8.setText(smallList.get(7));
        activity9.setText(smallList.get(8));
        activity10.setText(smallList.get(9));
    }

    /**
     * This method will set an eventHandler to every label, making sure that if the user clicks them it will be
     * redirected to the edit Activity page to edit the Activity with Id as the label displayed. It also sets the
     * listener for the search box
     */
    public void activateLabels() {
        activity1.setOnMouseClicked(e -> editActivity(((Label) e.getSource()).getText()));
        activity2.setOnMouseClicked(e -> editActivity(((Label) e.getSource()).getText()));
        activity3.setOnMouseClicked(e -> editActivity(((Label) e.getSource()).getText()));
        activity4.setOnMouseClicked(e -> editActivity(((Label) e.getSource()).getText()));
        activity5.setOnMouseClicked(e -> editActivity(((Label) e.getSource()).getText()));
        activity6.setOnMouseClicked(e -> editActivity(((Label) e.getSource()).getText()));
        activity7.setOnMouseClicked(e -> editActivity(((Label) e.getSource()).getText()));
        activity8.setOnMouseClicked(e -> editActivity(((Label) e.getSource()).getText()));
        activity9.setOnMouseClicked(e -> editActivity(((Label) e.getSource()).getText()));
        activity10.setOnMouseClicked(e -> editActivity(((Label) e.getSource()).getText()));

        searchBox.textProperty().addListener(((observable, oldValue, newValue) -> {
            previous.setDisable(false);
            next.setDisable(false);
            currentIndex = 0;
            instantiateActivities(true, true);
        }));
    }

    public void createActivity() {
        this.mainCtrl.goToEditActivity(null);
    }



}