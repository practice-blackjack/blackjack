package nulp.pist21.blackjack.message;

public class StringMessage {

    private String message;

    public StringMessage() {
        this.message = "";
    }

    public StringMessage(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}