package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.User;

public class UserDataResponse {

    public final User user;

    public UserDataResponse(User user) {
        this.user = user;
    }

}
