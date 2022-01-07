package user;

public class Message {

    private int fromUserId;
    private int toUserId;
    private String messageText;

    public Message(int fromUserId, int toUserId, String messageText) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.messageText = messageText;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public String toString() {
        return messageText;
    }
}
