package commons;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Player {
    /**
     * GenerationType.SEQUENCE : Hibernate requests the primary key value from a database sequence.
     * @SequenceGenerator annotation lets us define the name of the generator, the schema
     * of the database sequence and the allocation size of the sequence.
     */
    @Id
    @SequenceGenerator(name = "player_sequence",
            sequenceName = "player_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "player_sequence")
    private Long id;
    private String username;
    private int currentScore;
    @Transient
    private int timeLeft = 20; // #### CHANGE IN FINAL VERSION ####
    @Transient
    private List<JokerCard> jokerCards;//each player is assigned with a set of available jokers

    /**
     * Constructor for a player without ID parameter
     * @param username Player's username
     * @param currentScore Player's score
     */
    public Player(String username, int currentScore) {
        this.username = username;
        this.currentScore = currentScore;
    }

    public List<JokerCard> getJokerCards() {
        return jokerCards;
    }

    public void setJokerCards(List<JokerCard> jokerCards) {
        this.jokerCards = jokerCards;
    }

    /**
     * Constructor for a player. Creates a player with all given parameters
     * @param id Player's ID in the database (primary key)
     * @param username Player's username
     * @param currentScore Player's score
     */
    public Player(Long id, String username, int currentScore) {
        this.id = id;
        this.username = username;
        this.currentScore = currentScore;
    }

    /**
     * Empty constructor for a player
     */
    public Player() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return currentScore == player.currentScore && username.equals(player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, currentScore);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", currentScore=" + currentScore +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void deleteJoker(JokerCard joker) {
        jokerCards.remove(joker);
    }
}
