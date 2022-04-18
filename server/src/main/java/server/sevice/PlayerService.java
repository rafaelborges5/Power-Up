package server.sevice;

import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.PlayerRepository;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    /**
     * Creates an instance of this class PlayerService Class.
     * @param playerRepository  An instance of the repository class.
     */
    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Returns a list of players in the repository.
     * @return List of Player instances found the in the repository.
     */
    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    /**
     * Service layer method for adding a player to the repository.
     * @param player An instance of the Player Class
     */
    public void addPlayer(Player player) {
        playerRepository.save(player);
    }

    /**
     * Service layer method for deleting a player from the repository.
     * @param playerId Unique ID of the player to be deleted.
     */
    public void deletePlayer(Long playerId) {
        // check if this playerId exists
        boolean exists = playerRepository.existsById(playerId);
        if (!exists) {
            throw new IllegalStateException("Player with ID " + playerId + " does not exist.");
        }

        playerRepository.deleteById(playerId);
    }
}
