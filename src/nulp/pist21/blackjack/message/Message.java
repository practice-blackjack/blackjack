package nulp.pist21.blackjack.message;

public class Message {

    private String type;

    public Message() {
        this.type = "";
    }

    public Message(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
