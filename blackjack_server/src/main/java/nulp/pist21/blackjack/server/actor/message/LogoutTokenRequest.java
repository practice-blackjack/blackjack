package nulp.pist21.blackjack.server.actor.message;

public class LogoutTokenRequest {

    public final long token;

    public LogoutTokenRequest(long token) {
        this.token = token;
    }

}
