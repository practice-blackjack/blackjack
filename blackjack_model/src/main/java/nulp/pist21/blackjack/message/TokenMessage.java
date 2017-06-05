package nulp.pist21.blackjack.message;

public class TokenMessage extends Message {

    private long token;

    public TokenMessage() {
        this("", -1);
    }

    public TokenMessage(String type, long token) {
        super(type);
        this.token = token;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

}
