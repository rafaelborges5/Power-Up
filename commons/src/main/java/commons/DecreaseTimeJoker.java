package commons;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DecreaseTimeJoker extends JokerCard {
    public String senderUsername;
    public Player localPlayer;

    @JsonCreator
    public DecreaseTimeJoker(@JsonProperty("senderUsername") String senderUsername) {
        super("Decrease Time Joker","...",true);
        this.senderUsername = senderUsername;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    public int returnUseCard(){
        int decreasedTime = (int) Math.round(localPlayer.getTimeLeft()*0.25);
        useCard();
        return decreasedTime ;
    }

    @Override
    public void useCard() {
        if(!senderUsername.equals(localPlayer.getUsername())){
            localPlayer.setTimeLeft((int) Math.round(localPlayer.getTimeLeft()*0.75));
        }
    }
    public void setLocalPlayer(Player localPlayer){
        this.localPlayer = localPlayer;
    }
}
