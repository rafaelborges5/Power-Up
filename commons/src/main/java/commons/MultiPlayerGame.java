package commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class MultiPlayerGame extends Game{
    private ArrayList<Player> players;
    private HashMap<Player, Integer> leaderboard;   // live leaderboard

    /**
     *
     * @param questions Questions to be answered by players in this game.
     * @param jokerCards Joker cards to be made available to players in the game.
     * @param players Players playing in the game.
     */
    public MultiPlayerGame(ArrayList<Question> questions, ArrayList<JokerCard> jokerCards, ArrayList<Player> players) {
        super(questions, jokerCards);
        this.leaderboard = new HashMap<>();
        this.players = players;
        // generate leaderboard

//        for (Player player : players) {
//            this.leaderboard.put(player, player.getCurrentScore());
//        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public HashMap<Player, Integer> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(HashMap<Player, Integer> leaderboard) {
        this.leaderboard = leaderboard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiPlayerGame)) return false;
        if (!super.equals(o)) return false;
        MultiPlayerGame that = (MultiPlayerGame) o;
        return getPlayers().equals(that.getPlayers()) && getLeaderboard().equals(that.getLeaderboard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPlayers(), getLeaderboard());
    }

    @Override
    public String toString() {
        return "MultiPlayerGame{" +
                "players=" + players +
                ", leaderboard=" + leaderboard +
                '}';
    }


    /**
     * This method starts the timer for the questions. It also displays the time left for the player. Once the timer is
     * over this method will go to the intermediate screen
     */
    public void multiplayerInGameTimer(){
        Timer timer1 = new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                for(int i = 0; i < players.size(); i++){
                    boolean timeUp = true;
                    if(players.get(i).getTimeLeft() > 0){
                        players.get(i).setTimeLeft(players.get(i).getTimeLeft() - 1);
                        timeUp = false;
                    }
                    else{
                        players.get(i).setTimeLeft(0);
                        // 
                        // Method that disables the players options
                        //
                    }
                    if(timeUp){
                        timer1.cancel();
                        for (int j = 0; j < players.size(); j++){
                            players.get(i).setTimeLeft(20);     // resetting the timeLeft for each player
                        }
                        //
                        // Method that goes to the intermediate screen
                        //
                    }
                }
            }
        }, 0, 1000);
    }
}
