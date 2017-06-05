package nulp.pist21.blackjack.message;

public class TokenMessage extends StringMessage {

    private long token;

    public TokenMessage() {
        super("");
        token = -1;
    }

    public TokenMessage(String message, long token) {
        super(message);
        this.token = token;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

}
