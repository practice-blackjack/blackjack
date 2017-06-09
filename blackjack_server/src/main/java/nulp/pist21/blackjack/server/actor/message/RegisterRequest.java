package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.User;

public class RegisterRequest {

    public final User user;

    public RegisterRequest(User user) {
        this.user = user;
    }

}
