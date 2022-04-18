package commons;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SinglePlayerGame extends Game{
    private static Player player;

    /**
     * Creates an instance of a single-player game.
     * @param questions Questions to be answered by the player in this game.
     * @param jokerCards Joker cards to be made available to players in the game.
     * @param player Player participating in this game.
     */
    public SinglePlayerGame(ArrayList<Question> questions, ArrayList<JokerCard> jokerCards, Player player) {
        super(questions, jokerCards);
        this.player = player;
    }

    public SinglePlayerGame() {}

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        player.setJokerCards(getJokerCards());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SinglePlayerGame)) return false;
        if (!super.equals(o)) return false;
        SinglePlayerGame that = (SinglePlayerGame) o;
        return getPlayer().equals(that.getPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPlayer());
    }

    @Override
    public String toString() {
        return "SinglePlayerGame{" +
                "player=" + player +
                '}';
    }

    /**
     * This method starts the timer for the questions. It also displays the time left for the player. Once the timer is
     * over this method will go to the intermediate screen
     */
    public static void singleplayerInGameTimer(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(player.getTimeLeft() < 0){
                    timer.cancel();
                    player.setTimeLeft(3);// Resetting the time left for player. #### CHANGE TO 20 IN FINAL VERSION
                    //
                    // Method that checks if the answer of the user is right
                    //
                    // Method that goes to intermediate screen
                    //
                }
                else
                    player.setTimeLeft(player.getTimeLeft() - 1);

            }
        }, 0, 5000);
    }
}
