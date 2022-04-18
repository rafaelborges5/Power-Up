package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import commons.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import server.sevice.PlayerService;

import java.util.List;

/**
 * API layer of the Player class.
 */
@RestController
@RequestMapping("api/player")
public class PlayerController {
    private PlayerService playerService;

    /**
     * Creates an instance of PlayerController Class
     * @param playerService An instance of the utility class that links the API Layer to the Repository Layer
     */
    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Serves the user's GET request by invoking the getPlayers method from the playerService object
     * @return List of players returned by the service layer.
     */
    @GetMapping
    public List<Player> getPlayers(){
        return playerService.getPlayers();
    }

    /**
     * API layer method for the POST request
     * @param player An instance of the utility class that links the API Layer to the Repository Layer
     */
    @PostMapping
    public void addNewPlayer( @RequestBody Player player){ playerService.addPlayer(player);
    }

    /**
     * API layer method for DELETE request.
     * @param playerId ID of the player to be deleted.
     */
    @DeleteMapping(path="{playerId}")
    public void deletePlayer(@PathVariable("playerId") Long playerId) {
        playerService.deletePlayer(playerId);
    }
}
