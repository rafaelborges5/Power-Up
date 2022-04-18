/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import client.utils.ServerUtils;
import commons.*;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.inject.Inject;
import java.util.*;


public class MainCtrl {


    private ServerUtils serverUtils;

    protected Stage primaryStage;

    private MenuCtrl menuCtrl;
    private Scene menu;

    private SinglePlayerLobbyCtrl singlePlayerLobbyCtrl;
    private Scene singlePlayerLobby;

    private MultiPlayerLobbyCtrl multiPlayerLobbyCtrl;
    private Scene multiPlayerLobby;

    private SinglePlayerMultipleChoiceQuestionCtrl singlePlayerGameCtrl;
    private Scene singlePlayerGame;

    private CreditsCtrl creditsCtrl;
    private Scene credits;

    private SinglePlayerChooseOptionQuestionCtrl singlePlayerChooseOptionQuestionCtrl;
    private Scene singlePlayerChooseOptionQuestion;

    private SinglePlayerGuessQuestionCtrl singlePlayerGuessQuestionCtrl;
    private Scene singlePlayerGuessQuestion;

    private InsertUsernameMultiplayerCtrl multiplayerInsertInfoCtrl;
    private Scene multiplayerInsertInfo;

    private HelpCtrl helpCtrl;
    private Scene help;

    private InsertUsernameSinglePlayerCtrl singleplayerInsertInfoCtrl;
    private Scene singleplayerInsertInfo;

    private AdminPanelCtrl adminPanelCtrl;
    private Scene admin;

    private EditActivityCtrl editActivityCtrl;
    private Scene editActivity;

    private SinglePlayerLeaderboardCtrl singlePlayerLeaderboardCtrl;
    private Scene singlePlayerLeaderboard;

    private SingleplayerInsteadOfQuestionCtrl singleplayerInsteadOfQuestionCtrl;
    private Scene singleplayerInsteadOfQuestion;

    private IntermediateScreenCtrl intermediateScreenCtrl;
    private Scene intermediateScreen;

    private SingleplayerStartCountdownScreenCtrl singleplayerStartCountdownScreenCtrl;
    private Scene singlePlayerStartCountdownScreen;

    private ConfirmBoxCtrl confirmBoxCtrl;
    private Scene confirmBox;

    private MultiplayerIntermediateScreenCtrl multiplayerIntermediateScreenCtrl;
    private Scene multiPlayerIntermediateScreen;

    private Scene errorScreen;
    private ErrorScreenCtrl errorScreenCtrl;


    private Game game; // An instance of Game class representing the ongoing game
    private int gameId;
    private List<String> jokersStringList; // A list of Strings representing the names of the Jokers
    // that the player chose to use

    private String usedJoker;
    private boolean usedAdditionalPoints;
    boolean exitedGame;
    private Player localPlayer;

    private Stage popUpStage;

    private static int timeLeft;

    private static Stack<String> visitedScreens = new Stack<>();    // stores the screens visited in reverse order.

    private boolean usedQuestionChangeJoker = false;

    private int oldQuestionNumber = 0;

    @Inject
    public MainCtrl(ServerUtils serverUtils) {
        this.serverUtils = serverUtils;
    }


    /**
     * This method will take care of initializing all scenes present in the application and starting the app with the
     * menu
     *
     * @param primaryStage                                         the stage on which the app will be displayed
     * @param menuPair                                             the pair containing the menu controller and its fxml
     *                                                             file "Parent"
     * @param singlePlayerLobbyControllerParentPair                the pair containing the singlePlayerLobby controller
     *                                                             and its fxml
     *                                                             file "Parent"
     * @param multiPlayerLobbyControllerParentPair                 the pair containing the multiPlayerLobby controller
     *                                                             and its fxml file
     *                                                             "Parent"
     * @param creditsControllerParentPair                          the pair containing the credits controller and its
     *                                                             fxml file "Parent"
     * @param singlePlayerGamePair                                 the pair containing the singlePlayerGame controller
     *                                                             and its fxml file "Parent"
     *                                                             <<<<<<< HEAD
     *                                                             its fxml file "Parent"
     *                                                             =======
     *                                                             >>>>>>> development
     * @param singlePlayerChooseOptionQuestionControllerParentPair
     * @param singlePlayerOpenQuestionControllerParentPair
     */
    public void initialize(Stage primaryStage, Pair<MenuCtrl, Parent> menuPair, Pair<SinglePlayerLobbyCtrl,
            Parent> singlePlayerLobbyControllerParentPair, Pair<MultiPlayerLobbyCtrl,
            Parent> multiPlayerLobbyControllerParentPair, Pair<CreditsCtrl, Parent> creditsControllerParentPair,
                           Pair<SinglePlayerMultipleChoiceQuestionCtrl,
                                   Parent> singlePlayerGamePair,
                           Pair<SinglePlayerChooseOptionQuestionCtrl, Parent>
                                   singlePlayerChooseOptionQuestionControllerParentPair,
                           Pair<SinglePlayerGuessQuestionCtrl, Parent>
                                   singlePlayerOpenQuestionControllerParentPair,
                           Pair<InsertUsernameMultiplayerCtrl, Parent> insertInfoMultiplayer,
                           Pair<HelpCtrl, Parent> helpCtrlParentPair,
                           Pair<InsertUsernameSinglePlayerCtrl, Parent> insertInfoSingleplayer,
                           Pair<SinglePlayerLeaderboardCtrl, Parent> singlePlayerLeaderboardCtrlParentPair,
                           Pair<SingleplayerInsteadOfQuestionCtrl, Parent>
                                   singleplayerInsteadOfQuestionCtrlParentPair,
                           Pair<AdminPanelCtrl, Parent> adminPanel, Pair<EditActivityCtrl, Parent> editActivity,
                           Pair<IntermediateScreenCtrl, Parent> intermediateScreenCtrlParentPair,
                           Pair<SingleplayerStartCountdownScreenCtrl,
                                   Parent> singleplayerStartCountdownScreenCtrlParentPair, Pair<ConfirmBoxCtrl, Parent>
                                   confirmBox,
                           Pair<MultiplayerIntermediateScreenCtrl, Parent> multiplayerIntermediateScreenCtrlParentPair,
                           Pair<ErrorScreenCtrl, Parent> errorScreenCtrlParentPair) {


        this.primaryStage = primaryStage;
        this.menuCtrl = menuPair.getKey();
        this.menu = new Scene(menuPair.getValue());

        this.singlePlayerLobbyCtrl = singlePlayerLobbyControllerParentPair.getKey();
        this.singlePlayerLobby = new Scene(singlePlayerLobbyControllerParentPair.getValue());
        this.multiPlayerLobbyCtrl = multiPlayerLobbyControllerParentPair.getKey();
        this.multiPlayerLobby = new Scene(multiPlayerLobbyControllerParentPair.getValue());
        this.singlePlayerGameCtrl = singlePlayerGamePair.getKey();
        this.singlePlayerGame = new Scene(singlePlayerGamePair.getValue());
        this.creditsCtrl = creditsControllerParentPair.getKey();
        this.credits = new Scene(creditsControllerParentPair.getValue());
        this.singlePlayerChooseOptionQuestionCtrl = singlePlayerChooseOptionQuestionControllerParentPair.getKey();
        this.singlePlayerChooseOptionQuestion = new
                Scene(singlePlayerChooseOptionQuestionControllerParentPair.getValue());
        this.singlePlayerGuessQuestionCtrl = singlePlayerOpenQuestionControllerParentPair.getKey();
        this.singlePlayerGuessQuestion = new Scene(singlePlayerOpenQuestionControllerParentPair.getValue());
        this.multiplayerInsertInfoCtrl = insertInfoMultiplayer.getKey();
        this.multiplayerInsertInfo = new Scene(insertInfoMultiplayer.getValue());
        this.helpCtrl = helpCtrlParentPair.getKey();
        this.help = new Scene(helpCtrlParentPair.getValue());
        this.singleplayerInsertInfoCtrl = insertInfoSingleplayer.getKey();
        this.singleplayerInsertInfo = new Scene(insertInfoSingleplayer.getValue());
        this.adminPanelCtrl = adminPanel.getKey();
        this.admin = new Scene(adminPanel.getValue());
        this.editActivityCtrl = editActivity.getKey();
        this.editActivity = new Scene(editActivity.getValue());
        this.singleplayerInsteadOfQuestionCtrl = singleplayerInsteadOfQuestionCtrlParentPair.getKey();
        this.singleplayerInsteadOfQuestion = new Scene(singleplayerInsteadOfQuestionCtrlParentPair.getValue());
        this.singlePlayerLeaderboardCtrl = singlePlayerLeaderboardCtrlParentPair.getKey();
        this.singlePlayerLeaderboard = new Scene(singlePlayerLeaderboardCtrlParentPair.getValue());
        this.intermediateScreenCtrl = intermediateScreenCtrlParentPair.getKey();
        this.intermediateScreen = new Scene(intermediateScreenCtrlParentPair.getValue());
        this.singleplayerStartCountdownScreenCtrl = singleplayerStartCountdownScreenCtrlParentPair.getKey();
        this.singlePlayerStartCountdownScreen = new Scene(singleplayerStartCountdownScreenCtrlParentPair.getValue());
        this.confirmBoxCtrl = confirmBox.getKey();
        this.confirmBox = new Scene(confirmBox.getValue());
        this.multiplayerIntermediateScreenCtrl = multiplayerIntermediateScreenCtrlParentPair.getKey();
        this.multiPlayerIntermediateScreen = new Scene(multiplayerIntermediateScreenCtrlParentPair.getValue());
        this.errorScreenCtrl = errorScreenCtrlParentPair.getKey();
        this.errorScreen = new Scene(errorScreenCtrlParentPair.getValue());

        this.exitedGame = false;
        this.editActivity.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");
        this.admin.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");
        this.menu.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");
        this.intermediateScreen.getStylesheets().add("@../../stylesheets/singleplayer_game.css");
        this.credits.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");
        this.multiPlayerIntermediateScreen.getStylesheets().add("@../../stylesheets/singleplayer_game.css");
        this.singlePlayerLobby.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");
        this.singleplayerInsertInfo.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");
        this.multiplayerInsertInfo.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");
        this.singlePlayerGame.getStylesheets().add("@../../stylesheets/singleplayer_game.css");
        this.singlePlayerGuessQuestion.getStylesheets().add("@../../stylesheets/singleplayer_game.css");
        this.singlePlayerChooseOptionQuestion.getStylesheets().add("@../../stylesheets/singleplayer_game.css");
        this.singleplayerInsteadOfQuestion.getStylesheets().add("@../../stylesheets/singleplayer_game.css");
        this.singlePlayerLeaderboard.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");
        this.singlePlayerStartCountdownScreen.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");
        this.singleplayerInsertInfo.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");
        this.multiPlayerLobby.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");
        this.editActivity.getStylesheets().add("@../../stylesheets/menu_stylesheet.css");


        primaryStage.setTitle("Quizzz");
        goTo("menu");
        primaryStage.show();
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeStage();
        });
    }


    public static int getTimeLeft() {
        return timeLeft;
    }

    public static void setTimeLeft(int timeLeft) {
        MainCtrl.timeLeft = timeLeft;
    }

    /**
     * The method includes the logic of the game. We will retrieve a game object from the server that will contain
     * a player attribute with the given username. In this method we will iterate through all the questions,
     * by selecting the current question from the game attribute currentQuestionNumber in the game
     * and set the correct scene for each of them
     *
     * @param player Instance of Player representing the username inserted by the user
     */
    public void playSinglePLayerGame(Player player, int difficulty) {
        localPlayer = player;
        game = serverUtils.createSinglePlayerGame(player);
        for (int i = 0; i < game.getQuestions().size(); i++) {
            game.getQuestions().get(i).setAllowedTime(difficulty);
        }
        System.out.println(game.getQuestions().toString());
        goToNextQuestion();

    }


    /**
     * This is a timer that works in the background and switches to the next question
     */
    public void singleplayerInGameTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = game.getQuestions().get(game.getCurrentQuestionNumber()).getAllowedTime();

            @Override
            public void run() {
                if (exitedGame) {
                    timer.cancel();
                    setExitedGame(false);
                }
                if (i <= 0) {
                    timer.cancel();
                    if (usedQuestionChangeJoker) {
                        usedQuestionChangeJoker = false;
                        game.setCurrentQuestionNumber(oldQuestionNumber);
                    }

                    Platform.runLater(() -> {
                        int currentQuestionNumber1 = game.getCurrentQuestionNumber();
                        Question q1 = game.getQuestions().get(currentQuestionNumber1);
                        String className1 = getClassName(q1.getClass().getName());
                        intermediateScreenCtrl.setPointsLabel();
                        // if we are on the last question, don't go to the intermediate screen
                        if (currentQuestionNumber1 < game.getQuestions().size() - 1) {
                            goTo("intermediateScreen");
                        } else {
                            checkGameStatus();
                        }
                    });
                }
                if (exitedGame) {
                    timer.cancel();
                    setExitedGame(false);
                } else {
                    int currentQuestionNumber = 0;
                    if (!usedQuestionChangeJoker) {
                        currentQuestionNumber = game.getCurrentQuestionNumber();
                    }

                }
                int currentQuestionNumber = game.getCurrentQuestionNumber();
                Question q = game.getQuestions().get(currentQuestionNumber);
                String className = getClassName(q.getClass().getName());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        switch (className) {
                            case "MultipleChoiceQuestion":
                                singlePlayerGameCtrl.setTime(i + 1);
                                break;

                            case "MostEnergyQuestion":
                                singlePlayerChooseOptionQuestionCtrl.setTime(i + 1);
                                break;

                            case "GuessQuestion":
                                singlePlayerGuessQuestionCtrl.setTime(i + 1);
                                break;

                            case "InsteadOfQuestion":
                                singleplayerInsteadOfQuestionCtrl.setTime(i + 1);
                                break;
                            default:
                                break;
                        }
                    }
                });

                MainCtrl.setTimeLeft(i);
                i--;
            }
        }, 0, 1000);
    }

    /**
     * This method checks if the game is over. It is called after every question.
     */
    public void checkGameStatus() {
        if (game.getCurrentQuestionNumber() + 1 < game.getQuestions().size()) {
            game.setCurrentQuestionNumber(game.getCurrentQuestionNumber() + 1);
        } else {
            game.setGameOver(true);
        }
        goToNextQuestion();
    }

    private void goToNextQuestion() {
        if (!game.isGameOver()) {
            if (game instanceof SinglePlayerGame)
                singleplayerInGameTimer();
            else
                multiplayerInGameTimer();

            int currentQuestionNumber;

            currentQuestionNumber = game.getCurrentQuestionNumber();

            Question q = game.getQuestions().get(currentQuestionNumber);
            String className = getClassName(q.getClass().getName());

            switchQuestionScreen(className);
        } else {
            if (game instanceof SinglePlayerGame) {
                SinglePlayerGame spg = (SinglePlayerGame) this.game;
                Player p = spg.getPlayer();
                p.setJokerCards(null);
                serverUtils.addPlayer(spg.getPlayer());
            } else {
                MultiPlayerGame mpg = (MultiPlayerGame) this.game;
                ArrayList<Player> players = mpg.getPlayers();

                for (int i = 0; i < players.size(); i++) {
                    players.get(i).setJokerCards(null);
                }
            }
            goTo("SinglePlayerLeaderboard");  // PUT LEADERBOARD SCREEN HERE
        }
    }

    /**
     * This method takes an String input that includes the package and the class name and returns only the class name
     *
     * @param className String representing the package and the class name
     * @return String representing  the class name
     */
    public String getClassName(String className) {

        int mid = className.lastIndexOf('.') + 1;
        String finalClsName = className.substring(mid);
        return finalClsName;
    }

    public Game getGame() {
        return game;
    }

    /**
     * This is the method that handles when the user presses the exit button or the window close button
     */
    public void closeStage() {
        popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setTitle("Confirm");
        popUpStage.setMinWidth(250);
        popUpStage.setScene(confirmBox);
        popUpStage.show();
    }

    /**
     * This method will receive a parameter that determines whether to close the game or to ignore exit request
     *
     * @param answer true - close, false - ignore
     */
    public void setAnswer(boolean answer) {
        if (answer) {
            popUpStage.close();
            this.primaryStage.close();
            if (visitedScreens.peek().equals("multiLobby")) {
                multiPlayerLobbyCtrl.tearDown();
            }
        } else {
            popUpStage.close();
        }
    }

    /**
     * This method will take care of all scene switching of the application
     *
     * @param screenName the name of the screen for which it is desired to switch
     */
    @SuppressWarnings({"checkstyle:methodlength"})
    public void goTo(String screenName) {
        visitedScreens.push(screenName);
        switch (screenName) {
            case "menu":
                primaryStage.setScene(menu);
                break;
            case "singleGame":
                primaryStage.setScene(singleplayerInsertInfo);
                singleplayerInsertInfoCtrl.prepare();
                break;
            case "credits":
                primaryStage.setScene(credits);
                break;
            case "singleLobby":
                singlePlayerLobbyCtrl.resetJokers();
                jokersStringList = new ArrayList<>();
                primaryStage.setScene(singlePlayerLobby);
                break;
            case "multiLobby":
                multiPlayerLobbyCtrl.setThisPlayer(localPlayer);
                primaryStage.setScene(multiPlayerLobby);
                multiPlayerLobbyCtrl.prepare();
                break;
            case "insertInfoMultiPlayer":
                primaryStage.setScene(multiplayerInsertInfo);
                multiplayerInsertInfoCtrl.prepare();
                break;
            case "help":
                primaryStage.setScene(help);
                break;
            case "singleplayerGame":
                primaryStage.setScene(singlePlayerGame);
                break;
            case "SingleplayerChooseOptionQuestionScreen":
                primaryStage.setScene(singlePlayerChooseOptionQuestion);
                break;
            case "SingleplayerOpenQuestion":
                primaryStage.setScene(singlePlayerGuessQuestion);
                break;
            case "SingleplayerInsteadOfQuestion":
                primaryStage.setScene(singleplayerInsteadOfQuestion);
                break;
            case "SinglePlayerLeaderboard":
                primaryStage.setScene(singlePlayerLeaderboard);
                singlePlayerLeaderboardCtrl.initialiseLeaderboard();
                break;
            case "intermediateScreen":
                intermediateScreenCtrl.initialiseScene();
                primaryStage.setScene(intermediateScreen);
                break;
            case "admin":
                primaryStage.setScene(admin);
                adminPanelCtrl.searchBox.clear();
                if (serverUtils.testConnection()) {
                    adminPanelCtrl.setListOfActivities(serverUtils.getActivities());
                } else {
                    goTo("error");
                    break;
                }
                adminPanelCtrl.activateLabels();
                adminPanelCtrl.instantiateActivities(true, true);
                break;
            case "multiplayerIntermediateScreen":
                multiplayerIntermediateScreenCtrl.initialiseLeaderboard();
                primaryStage.setScene(multiPlayerIntermediateScreen);
                multiplayerIntermediateScreenCtrl.startCountdown();
                break;
            case "error":
                primaryStage.setScene(errorScreen);
                break;
            default:
                primaryStage.setScene(menu);
        }

    }

    /**
     * This method will route the primaryStage to display the EditActivity scene. It will also do so taking into account
     * whether to display it in Edit mode or Add mode. This will be decided based on whether the activity to display is
     * null or not
     *
     * @param activity to display
     */
    public void goToEditActivity(Activity activity) {
        if (activity != null) {
            primaryStage.setScene(editActivity);
            editActivityCtrl.initialize(activity);
        } else {
            editActivityCtrl.prepareAddActivity();
            primaryStage.setScene(editActivity);
        }
    }

    /**
     * This method is implemented for the Question Change Joker and we don't need a timer because
     * we have one from the question that has been changed
     */
    public void goToExtraQuestion() {

        Question q = game.getQuestions().get(0);
        String className = getClassName(q.getClass().getName());
        this.switchQuestionScreen(className);
    }


    public void startSinglePlayerGameCountdown(Player player) {
        primaryStage.setScene(singlePlayerStartCountdownScreen);
        singleplayerStartCountdownScreenCtrl.startCountdown(player);
    }

    public void setStringJokers(List<String> checkedStringJokers) {
        this.jokersStringList = checkedStringJokers;
    }

    public List<String> getStringJokers() {
        return this.jokersStringList;
    }

    /**
     * This method creates a player instance with the given username. It also instantiates each joker
     * transforming them from a String to an JokerCard instance
     *
     * @param insertedUsername String representing the username inserted by the user
     * @param stringJokers     List of Strings representing the names of the jokers that have to be instantiated.
     * @return An instance of the Player Class
     */
    public Player createPlayer(String insertedUsername, List<String> stringJokers) {
        Player p = new Player(insertedUsername, 0);
        List<JokerCard> jokerList = new ArrayList<>();
        System.out.println(stringJokers);
        for (String s : stringJokers) {
            switch (s) {
                case "Additional Points Joker":
                    jokerList.add(new AdditionalPointsJoker(p));
                    break;
                case "Eliminate Option Joker":
                    jokerList.add(new EliminateOptionJoker(null));
                    break;
                case "Question Change Joker":
                    jokerList.add(new QuestionChangeJoker());
                    break;
                default:
                    break;
            }
        }
        for (int i = 0; i < jokerList.size(); i++) {
            System.out.println(jokerList.get(i));
        }
        System.out.println("---------");
        p.setJokerCards(jokerList);

        return p;
    }

    /**
     * This method initialises the last joker that has been used.
     *
     * @param name String representing the name of the joker that has been used.
     */

    public void setUsedJoker(String name) {
        usedJoker = name;
    }

    /**
     * @param eliminateOptionJokerJoker
     */
    public void handleEliminateOptionJoker(EliminateOptionJoker eliminateOptionJokerJoker) {
        Question currentQuestion = game.getQuestions().
                get((game).
                        getCurrentQuestionNumber());
        if (currentQuestion instanceof InsteadOfQuestion) {
            singleplayerInsteadOfQuestionCtrl.initialiseAfterJoker();
        } else {
            eliminateOptionJokerJoker.setQuestion(currentQuestion);
            eliminateOptionJokerJoker.useCard();
        }
        if (currentQuestion instanceof MultipleChoiceQuestion) {
            singlePlayerGameCtrl.initialiseSinglePlayerQuestion();
        } else if (currentQuestion instanceof MostEnergyQuestion) {
            singlePlayerChooseOptionQuestionCtrl.initialiseMostEnergyQuestion();
        }
    }


    /**
     * This method handles each type of Joker and calls their useCard methods.
     */
    public void handleJoker() {
        switch (usedJoker) {
            case "Additional Points Joker":
                AdditionalPointsJoker pointsJoker =
                        (AdditionalPointsJoker) this.getJoker("Additional Points Joker");
                pointsJoker.setQuestion(game.getQuestions().get(game.getCurrentQuestionNumber()));
                pointsJoker.setPlayer(localPlayer);
                pointsJoker.useCard();
                System.out.println("Used additional points joker");
                if (game instanceof MultiPlayerGame) {
                    serverUtils.send("/app/jokerAlert/" + getGameId(),
                            new JokerAlert(localPlayer.getUsername(), pointsJoker.getName()));
                }
                localPlayer.deleteJoker(pointsJoker);

                break;

            case "Eliminate Option Joker":
                EliminateOptionJoker eliminateOptionJoker =
                        (EliminateOptionJoker) this.getJoker("Eliminate Option Joker");
                handleEliminateOptionJoker(eliminateOptionJoker);
                if (game instanceof MultiPlayerGame) {
                    String Path = "/app/jokerAlert/" + getGameId();

                    JokerAlert ja = new JokerAlert(localPlayer.getUsername(), eliminateOptionJoker.getName());
                    serverUtils.send(Path,
                            ja);
                }
                localPlayer.deleteJoker(eliminateOptionJoker);
                break;
            case "Question Change Joker":

                QuestionChangeJoker questionChangeJoker =
                        (QuestionChangeJoker) this.getJoker("Question Change Joker");

                oldQuestionNumber = game.getCurrentQuestionNumber();
                localPlayer.deleteJoker(questionChangeJoker);

                game.setCurrentQuestionNumber(0);

                this.goToExtraQuestion();

                usedQuestionChangeJoker = true;
                break;

            case "Decrease Time Joker":
                DecreaseTimeJoker timeJoker = (DecreaseTimeJoker) this.getJoker("Decrease Time Joker");
                timeJoker.setSenderUsername(localPlayer.getUsername());
                serverUtils.send("/app/timeJoker/" + this.getGameId(), timeJoker);
                serverUtils.send("/app/jokerAlert/" + this.getGameId(),
                        new JokerAlert(localPlayer.getUsername(), timeJoker.getName()));
                localPlayer.deleteJoker(timeJoker);
                break;

        }

    }

    /**
     * This method iterates through all the jokers that the player has and returns the one that has the sam name as
     * the parameter
     *
     * @param jokerName String representing the name of the Joker that we need
     * @return An instance of JokerCard
     */

    private JokerCard getJoker(String jokerName) {
        JokerCard returnedJokerCard = null;
        for (JokerCard j : localPlayer.getJokerCards()) {
            if (j.getName().equals(jokerName))
                returnedJokerCard = j;
        }
        return returnedJokerCard;
    }

    /**
     * This method initialises and goe sto the screen given by the className
     *
     * @param className String representing the className ( the type of question that we have to display)
     */
    public void switchQuestionScreen(String className) {
        switch (className) {
            case "MultipleChoiceQuestion":
                singlePlayerGameCtrl.initialiseSinglePlayerQuestion();
                goTo("singleplayerGame");
                break;
            case "MostEnergyQuestion":
                singlePlayerChooseOptionQuestionCtrl.initialiseMostEnergyQuestion();
                goTo("SingleplayerChooseOptionQuestionScreen");
                break;
            case "GuessQuestion":
                singlePlayerGuessQuestionCtrl.initialiseSinglePlayerOpenQuestion();
                goTo("SingleplayerOpenQuestion");
                break;
            case "InsteadOfQuestion":
                singleplayerInsteadOfQuestionCtrl.initialiseSinglePlayerInsteadOfQuestion();
                goTo("SingleplayerInsteadOfQuestion");
                break;
            default:
                break;
        }
    }


    public void setExitedGame(boolean exitedGame) {
        this.exitedGame = exitedGame;

    }

    public ServerUtils getServer() {
        return serverUtils;
    }


    /**
     * This method will call serverUtils to update the activity provided in the repository
     *
     * @param activity to update
     * @return the updated activity
     */
    public Activity editActivity(Activity activity) {
        return serverUtils.editActivity(activity);
    }


    public Player getLocalPlayer() {
        return localPlayer;
    }

    public void setLocalPlayer(Player player) {
        this.localPlayer = player;
    }

    /**
     * This method starts scanning for any updates to the scores of the players in the game
     */
    public void startScanningScoreUpdates() {
        serverUtils.registerForScoreUpdates("/topic/updateScore", p -> {
            for (int i = 0; i < serverUtils.getCurrentMultiplayerGame().getPlayers().size(); i++) {
                if (serverUtils.getCurrentMultiplayerGame().getPlayers().get(i).getUsername()
                        .equals(p.getUsername())) {
                    serverUtils.getCurrentMultiplayerGame().getPlayers().get(i)
                            .setCurrentScore(p.getCurrentScore());
                }
            }
        });
    }

    /**
     * This method starts the websocket connection for Emoji instances when the game is started, the method will be
     * called from the Lobby Screen , when the start button is pressed. It also initialises the emojis that are
     * retrieved from other clients.
     */
    public void startScanningEmojis() {
        serverUtils.registerForEmoji("/topic/emojis/" + this.getGameId(), e -> {
            String currentQuestionScreen = getClassName(game.getQuestions().
                    get(game.getCurrentQuestionNumber())
                    .getClass().toString());
            switch (currentQuestionScreen) {
                case "MultipleChoiceQuestion":
                    Platform.runLater(() -> {
                        singlePlayerGameCtrl.initialiseEmoji(e);
                    });
                    break;

                case "MostEnergyQuestion":
                    Platform.runLater(() -> {
                        singlePlayerChooseOptionQuestionCtrl.initialiseEmoji(e);
                    });
                    break;

                case "GuessQuestion":
                    Platform.runLater(() -> {
                        singlePlayerGuessQuestionCtrl.initialiseEmoji(e);
                    });
                    break;

                case "InsteadOfQuestion":
                    Platform.runLater(() -> {
                        singleplayerInsteadOfQuestionCtrl.initialiseEmoji(e);
                    });
                    break;

                default:
                    break;
            }
        });
    }

    /**
     *
     */
    public void startScanningJokerAlert() {
        serverUtils.registerForJokerAlert("/topic/jokerAlert/" + this.getGameId(), jokerAlert -> {
            String currentQuestionScreen = getClassName(game.getQuestions().
                    get(game.getCurrentQuestionNumber())
                    .getClass().toString());
            switch (currentQuestionScreen) {
                case "MultipleChoiceQuestion":
                    Platform.runLater(() -> {
                        singlePlayerGameCtrl.initialisejokerAlert(jokerAlert);
                    });
                    break;

                case "MostEnergyQuestion":
                    Platform.runLater(() -> {
                        singlePlayerChooseOptionQuestionCtrl.initialisejokerAlert(jokerAlert);
                    });
                    break;

                case "GuessQuestion":
                    Platform.runLater(() -> {
                        singlePlayerGuessQuestionCtrl.initialisejokerAlert(jokerAlert);
                    });
                    break;

                case "InsteadOfQuestion":
                    Platform.runLater(() -> {
                        singleplayerInsteadOfQuestionCtrl.initialisejokerAlert(jokerAlert);
                    });
                    break;

                default:
                    break;
            }
        });
    }

    /**
     *
     */
    public void startScanningTimeJoker() {
        serverUtils.registerForTimeJoker("/topic/timeJoker/" + this.getGameId(), j -> {

            j.setLocalPlayer(localPlayer);

            if (!localPlayer.getUsername().equals(j.getSenderUsername())) {
                int decreasedTime = j.returnUseCard();
                multiplayerIntermediateScreenCtrl.setI(decreasedTime);
                String currentQuestionScreen = getClassName(game.getQuestions().
                        get(game.getCurrentQuestionNumber())
                        .getClass().toString());
            }
        });
    }

    /**
     * The method includes the logic of the multiplayer game but it is not fully implemented.
     */
    public void startMultiPlayerGame() {
        System.out.println(localPlayer.getUsername());
        serverUtils.registerForScoreUpdates("/topic/updateScores/" + gameId, q -> {
            MultiPlayerGame multiGame = (MultiPlayerGame) game;
            for (int i = 0; i < multiGame.getPlayers().size(); i++) {
                if (multiGame.getPlayers().get(i).getUsername().equals(q.getUsername())) {
                    multiGame.getPlayers().get(i).setCurrentScore(q.getCurrentScore());
                    System.out.println("ping from " + q.getUsername()); //test
                }
            }
        });
        startScanningEmojis();
        startScanningScoreUpdates();
        startScanningTimeJoker();
        startScanningJokerAlert();
        localPlayer.setJokerCards(getJokerList());
    }

    public void playMultiPLayerGame() {
        goToNextQuestion();
    }

    private List<JokerCard> getJokerList() {

        List<JokerCard> jokerList = new ArrayList<>();
        jokerList.add(new AdditionalPointsJoker(localPlayer));

        jokerList.add(new EliminateOptionJoker(null));


        jokerList.add(new DecreaseTimeJoker(localPlayer.getUsername()));
        //jokerList.add(new QuestionChangeJoker());

        return jokerList;

    }

    public Stack<String> getVisitedScreens() {
        return visitedScreens;
    }

    public static void setVisitedScreens(Stack<String> visitedScreens) {
        MainCtrl.visitedScreens = visitedScreens;
    }

    /**
     * This is the main timer for the multiplayer game. This method starts the timer, displays the time left, and
     * switches between the question and intermediate screens
     */
    public void multiplayerInGameTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            MultiPlayerGame multiPlayerGame = (MultiPlayerGame) game;

            @Override
            public void run() {
                if (exitedGame) {
                    timer.cancel();
                    setExitedGame(false);
                }
                if (game instanceof MultiPlayerGame) {
                    MultiPlayerGame multiPlayerGame = (MultiPlayerGame) game;
                }
                if (localPlayer.getTimeLeft() <= 0) {
                    timer.cancel();
                    localPlayer.setTimeLeft(20);
                    Platform.runLater(() -> goTo("multiplayerIntermediateScreen"));
                }
                int currentQuestionNumber = game.getCurrentQuestionNumber();
                Question q = game.getQuestions().get(currentQuestionNumber);
                String className = getClassName(q.getClass().getName());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        switch (className) {
                            case "MultipleChoiceQuestion":
                                singlePlayerGameCtrl.setTime(localPlayer.getTimeLeft()+1);
                                break;

                            case "MostEnergyQuestion":
                                singlePlayerChooseOptionQuestionCtrl.setTime(localPlayer.getTimeLeft()+1);
                                break;

                            case "GuessQuestion":
                                singlePlayerGuessQuestionCtrl.setTime(localPlayer.getTimeLeft()+1);
                                break;

                            case "InsteadOfQuestion":
                                singleplayerInsteadOfQuestionCtrl.setTime(localPlayer.getTimeLeft()+1);
                                break;
                            default:
                                break;
                        }
                    }
                });
                localPlayer.setTimeLeft(localPlayer.getTimeLeft() - 1);
            }
        }, 0, 1000);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return this.gameId;
    }

    public SingleplayerStartCountdownScreenCtrl getSingleplayerStartCountdownScreenCtrl() {
        return singleplayerStartCountdownScreenCtrl;
    }
}


