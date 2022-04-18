package server.database;

import org.springframework.data.jpa.repository.JpaRepository;
import commons.Player;
import org.springframework.stereotype.Repository;

/**
 * Repository for the player class
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}
