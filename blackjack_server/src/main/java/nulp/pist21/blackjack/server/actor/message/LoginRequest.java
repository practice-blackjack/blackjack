package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.User;

public class LoginRequest {

    public final User user;

    public LoginRequest(User user) {
        this.user = user;
    }

}
