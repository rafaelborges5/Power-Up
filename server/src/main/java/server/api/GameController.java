package server.api;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.sevice.GameService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    /**
     * This method will retrieve a list of mostEnergyQuestions
     * @return the list of mostEnergyQuestions
     */
    @GetMapping(path = "singleGame/mostEnergy")
    public ArrayList<MostEnergyQuestion> getListMostEnergy() {
        return gameService.getListMostEnergy();
    }

    @GetMapping(path = "singleGame/guess")
    public ArrayList<GuessQuestion> getListGuess() {
        return gameService.getListGuessQuestion();
    }

    @GetMapping(path = "singleGame/multipleChoice")
    public ArrayList<MultipleChoiceQuestion> getListMultipleChoice() {
        return gameService.getListMultipleChoice();
    }

    @GetMapping(path = "singleGame/insteadOf")
    public ArrayList<InsteadOfQuestion> getListInsteadOf() {
        return gameService.getListInsteadOf();
    }


    //MULTIPLAYER GAME LOGIC

    /**
     * This method will accept a player and propagate it to all those who are subscribed in the /topic/updateLobby
     * @param player to propagate
     * @return the same player
     */
    @MessageMapping("/updateLobby")
    @SendTo("/topic/updateLobby")
    public Player propagateNewPlayer(Player player) {
        List<Player> players = gameService.getCurrentMultiGame().getPlayers();
        if(players.contains(player)) {
            players.remove(player);
        } else {
            players.add(player);
        }
        return player;
    }

    /**
     * This method will propagate the player with new score to all those who are subscribed to /topic/updateScores/ +
     * the id of the game. This way we avoid sending the score to all players in games that don't have this player
     * @param player to propagate
     */
    @MessageMapping("/updateScores/{id}")
    @SendTo("/topic/updateScores/{id}")
    public Player propagateUpdatedScore(Player player){
        return player;
    }

    /**
     * This method is destined for messages related to the start of the game. Whenever a player clicks start it sends
     * a message that will stop here before being forwarded to everyone
     * @param check
     * @return
     */
    @MessageMapping("/startGame")
    @SendTo("/topic/startGame")
    public boolean startGame(boolean check) {
        gameService.archiveGame();
        return check;
    }

    /**
     * This method will get the list of MostEnergyQuestion that are in the MultiPlayerGame that currently has an active
     * lobby
     * @return the list of MostEnergyQuestion
     */
    @GetMapping(path = "multiGame/mostEnergy")
    public ArrayList<MostEnergyQuestion> getCurrentListMostEnergy() {
        return (ArrayList<MostEnergyQuestion>) gameService.getCurrentMultiGame().getQuestions().stream().
                filter(q -> (q instanceof MostEnergyQuestion)).
                map(q -> (MostEnergyQuestion) q).
                collect(Collectors.toList());
    }

    @GetMapping(path = "multiGame/guess")
    public ArrayList<GuessQuestion> getCurrentListGuess() {
        return (ArrayList<GuessQuestion>) gameService.getCurrentMultiGame().getQuestions().stream().
                filter(q -> (q instanceof GuessQuestion)).
                map(q -> (GuessQuestion) q).
                collect(Collectors.toList());
    }

    @GetMapping(path = "multiGame/multipleChoice")
    public ArrayList<MultipleChoiceQuestion> getListCurrentMultipleChoice() {
        return (ArrayList<MultipleChoiceQuestion>) gameService.getCurrentMultiGame().getQuestions().stream().
                filter(q -> (q instanceof MultipleChoiceQuestion)).
                map(q -> (MultipleChoiceQuestion) q).
                collect(Collectors.toList());
    }
    @GetMapping(path = "multiGame/insteadOf")
    public ArrayList<InsteadOfQuestion> getCurrentListInsteadOf() {
        return (ArrayList<InsteadOfQuestion>) gameService.getCurrentMultiGame().getQuestions().stream().
                filter(q -> (q instanceof InsteadOfQuestion)).
                map(q -> (InsteadOfQuestion) q).
                collect(Collectors.toList());
    }

    /**
     * This method will return the list of players that are currently in the MultiPlayerGame that has an active
     * lobby
     * @return the list of players
     */
    @GetMapping(path = "multiGame/players")
    public ArrayList<Player> getCurrentListPlayers() {
        return gameService.getCurrentMultiGame().getPlayers();
    }

    @GetMapping(path = "multiGame/gameId")
    public int getCurrentGameId() {
        return gameService.getActiveGamesSize();
    }

}
