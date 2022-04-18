package commons;

public class JokerAlert {
    private String senderUsername;
    private String jokerType;

    public JokerAlert(String senderUsername, String jokerType) {
        this.senderUsername = senderUsername;
        this.jokerType = jokerType;
    }
    public JokerAlert(){}

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getJokerType() {
        return jokerType;
    }

    public void setJokerType(String jokerType) {
        this.jokerType = jokerType;
    }
}
