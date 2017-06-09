package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.User;

public class RefreshUsersToken {

    public final User user;

    public RefreshUsersToken(User user) {
        this.user = user;
    }

}
