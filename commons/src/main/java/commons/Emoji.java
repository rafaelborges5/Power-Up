package commons;

public class Emoji {
    public String sender;// String representing the Player's username
    public String emojiPath;// String representing the URL of the emoji image

    public Emoji(String sender, String emojiPath) {
        this.sender = sender;
        this.emojiPath = emojiPath;
    }
    public Emoji(){}


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getEmojiPath() {
        return emojiPath;
    }

    public void setEmojiPath(String emojiPath) {
        this.emojiPath = emojiPath;
    }
}
