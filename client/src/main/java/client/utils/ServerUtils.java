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
package client.utils;


import commons.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static String SERVER = "http://localhost:8080/";
    private static String WEBSOCKETSERVER =
            SERVER.replaceAll("http", "ws").replaceAll("https", "ws");
    private static int multiGameIndex;

    public void setSERVER(String SERVER) {
        ServerUtils.SERVER = SERVER;
        WEBSOCKETSERVER = SERVER.replaceAll("http", "ws").replaceAll("https", "ws");
        session = connect(WEBSOCKETSERVER + "/websocket");
        System.out.println(WEBSOCKETSERVER);
    }

    /**
     * This method checks if the connection with the server has been established.
     * It is meant to test whether the user provides a correct server URL.
     * @param SERVER server to test the connection for
     * @return true if the query for the server is successful, false if it fails.
     */
    public boolean testConnection(String SERVER){
        try{
            List<Player> list = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/player")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<List<Player>>() {});
            return true;
        }
        catch (Exception e) {
            System.out.println("The server url is invalid! ");
            return false;
        }
    }

    /**
     * This method checks if the connection with the server has been established.
     * It is meant to test weather the user has a server running on localhost
     * @return true if the query for the server is successful, false if it fails.
     */
    public boolean testConnection() {
        try{
            List<Player> list = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/player")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<List<Player>>() {});
            return true;
        }
        catch (Exception e) {
            System.out.println("The server url is invalid! ");
            return false;
        }
    }

    public String getServer(){
        return SERVER;
    }


    public Activity addActivity(Activity activity) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activity") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(activity, APPLICATION_JSON), Activity.class);
    }

    /**
     * This method will get the list of all the activities that exist in the repository
     * @return the list of all activities
     */
    public List<Activity> getActivities() {
        List<Activity> list = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activity")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
        return list;
    }

    /**
     * This method will make a put request to the server to update the activity with Id as the id in the provided
     * to match the other fields of the provided activity
     * @param activity with the fields to update
     * @return the updated Activity
     */
    public Activity editActivity(Activity activity) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activity")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(activity, APPLICATION_JSON), Activity.class);
    }

    /**
     * This method will retrieve an activity from the server based on the id provided
     * @param id to retrieve the activity
     * @return the retrieved activity
     */
    public Activity getActivityById(String id) {
        Activity activity = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activity/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
        return activity;
    }

    /**
     * This method will delete the activity from the repository of activities
     * @param activity to delete
     * @return HTTP Response
     */
    public Response deleteActivity(Activity activity) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activity/" + activity.getId())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * This method will make a post request to the server with an ImagePacket which contains a string with the
     * serialized image
     * @param file the image packet to send
     */
    public void uploadImage(ImagePacket file) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activity/image")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(file, APPLICATION_JSON), ImagePacket.class);
    }

    /**
     * This method will make a simple request for the leaderboard to the server
     * @return the List of players representing the leaderboard
     */

    public List<Player> getLeaderboard() {
        List<Player> leaderboard = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/player")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Player>>() {});
        return leaderboard;
    }

    /**
     * This method will make a POST request to the server with the new Player
     * @param player the Player to add to the leaderboard/database
     * @return the same Player in case it is needed
     */

    public Player addPlayer(Player player) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/player")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(player, APPLICATION_JSON), Player.class);
    }

    /**
     * This method will make a request for the server to get a new Game with all the needed attributes
     * Keep in mind that the player in the game is the one you provide and the list of jokers will be empty
     * @param player to add to the game
     * @return the Game object
     */

    public Game createSinglePlayerGame(Player player) {
        ArrayList<Question> questions = new ArrayList<>();
        questions.addAll(getListMostEnergy());
        questions.addAll(getListInsteadOf());
        questions.addAll(getListMultipleChoice());
        questions.addAll(getListGuessQuestion());
        Collections.shuffle(questions);
        SinglePlayerGame game = new SinglePlayerGame(questions, new ArrayList<JokerCard>(), player);
        return game;
    }


    /**
     * This method will make a request for list of MostEnergyQuestions
     * @return a list of random MostEnergyQuestions from the server
     */
    public List<MostEnergyQuestion> getListMostEnergy() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/singleGame/mostEnergy")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * This method will make a request for list of InsteadOf questions
     * @return a list of random InsteafOfQuestion from the server
     */
    public List<InsteadOfQuestion> getListInsteadOf() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/singleGame/insteadOf")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * This method will make a request for list of MultipleChoiceQuestion
     * @return a list of random MultipleChoiceQuestion from the server
     */
    public List<MultipleChoiceQuestion> getListMultipleChoice() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/singleGame/multipleChoice")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * This method will make a request for list of GuessQuestion
     * @return a list of random GuessQuestion from the server
     */
    public List<GuessQuestion> getListGuessQuestion() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/singleGame/guess")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    //  MULTIPLAYER GAME LOGIC

    private StompSession session; //the StompSession

    /**
     * This method configures the StompSession for the websocket
     * @param url to connect
     * @return the StomSession of the websocket
     */
    private StompSession connect(String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {}).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    /**
     * This method will retrieve the current MultiGame that has an active lobby, with all the players that are currently
     * in the lobby. It shuffles the question list deterministically
     * @return the MultiPlayerGame Object
     */
    public MultiPlayerGame getCurrentMultiplayerGame() {
        ArrayList<Question> questions = new ArrayList<>();
        questions.addAll(getCurrentMultiGameMostEnergy());
        questions.addAll(getCurrentMultiGameGuess());
        questions.addAll(getCurrentMultiGameInsteadOf());
        questions.addAll(getCurrentMultiGameMultipleChoice());
        Collections.shuffle(questions, new Random(69));
        return new MultiPlayerGame(questions, new ArrayList<>(),new ArrayList<Player>(getCurrentMultiGamePlayers()));
    }

    /**
     * This method will send the new player through the websocket
     * @param player to send
     */
    public void sendPlayer(Player player) {
        this.send("/app/updateLobby", player);
    }

    /**
     * This method will send the updated player through the websocket to those subscribed to the topic that has the same
     * gameId
     * @param player to send
     */
    public void updatePlayerScore(Player player, int gameId){
        this.send("/app/updateScores/" + gameId, player);
    }

    /**
     * This method will get the list of MostEnergyQuestions that are in the MultiPlayerGame that is being retrieved
     * @return the list of MostEnergyQuestion
     */
    public List<MostEnergyQuestion> getCurrentMultiGameMostEnergy() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/multiGame/mostEnergy")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    public List<InsteadOfQuestion> getCurrentMultiGameInsteadOf() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/multiGame/insteadOf")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    public List<GuessQuestion> getCurrentMultiGameGuess() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/multiGame/guess")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    public List<MultipleChoiceQuestion> getCurrentMultiGameMultipleChoice() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/multiGame/multipleChoice")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    public int getCurrentMultiplayerGameId() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/multiGame/gameId")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * This method will retrieve the list of players that are currently in the MultiGame. Keep in mind more players can
     * join still, and that is why we have the method registerForNewPlayers
     * @return the list of players
     */
    public List<Player> getCurrentMultiGamePlayers() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/multiGame/players")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * This method will listen for a topic in the websocket session with path as the one in the destination. It is
     * expecting Objects of type player.
     * @param dest the topic of the websocket to listen to
     * @param consumer the Consumer that represents the action that this method is supposed to execute when on trigger
     *                 of the topic. This is to be passed as a lambda function
     */
    public void registerForNewPlayers(String dest, Consumer<Player> consumer) {
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Player.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((Player) payload);
            }
        });
    }

    /**
     * This method will listen for a topic in the websocket session with path as the one in the destination. It is
     * expecting Objects of type Player.
     * @param dest the topic of the websocket to listen to
     * @param consumer the Consumer that represents the action that this method is supposed to execute when on trigger
     *                 of the topic. This is to be passed as a lambda function
     */
    public void registerForScoreUpdates(String dest, Consumer<Player> consumer){
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Player.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((Player) payload);
            }
        });
    }

    /**
     * This method will listen for a topic in the websocket session with path as the one in the destination. It is
     * expecting Objects of type Emoji.
     * @param dest the topic of the websocket to listen to
     * @param consumer the Consumer that represents the action that this method is supposed to execute when on trigger
     *                 of the topic. This is to be passed as a lambda function
     */
    public void registerForEmoji(String dest,Consumer<Emoji> consumer){
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Emoji.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((Emoji) payload);
            }
        });

    }

    /**
     *
     * @param dest
     * @param consumer
     */
    public void registerForJokerAlert(String dest,Consumer<JokerAlert> consumer){
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return JokerAlert.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((JokerAlert) payload);
            }
        });

    }

    /**
     *
     * @param dest
     * @param consumer
     */
    public void registerForTimeJoker(String dest,Consumer<DecreaseTimeJoker> consumer){
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return DecreaseTimeJoker.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((DecreaseTimeJoker) payload);
            }
        });

    }

    /**
     * This method will listen for messages regarding the start of the game. Whenever the server propagates the
     * startGame message on the server it wil
     * @param dest
     * @param consumer
     */
    public void registerForGameStart(String dest, Consumer<Boolean> consumer) {
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Boolean.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((Boolean) payload);
            }
        });
    }


    /**
     * This method will send ,to the websocket destination provided, the object o
     * @param dest the path on the websocket
     * @param o the object to send
     */
    public void send(String dest, Object o) {

        session.send(dest, o);
    }



}