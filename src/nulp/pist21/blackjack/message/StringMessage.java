package nulp.pist21.blackjack.message;

public class StringMessage extends Message {

    private String message;

    public StringMessage() {
        this("", "");
    }

    public StringMessage(String type, String message) {
        super(type);
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}