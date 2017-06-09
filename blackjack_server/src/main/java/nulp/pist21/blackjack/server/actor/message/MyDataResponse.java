package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.User;

public class MyDataResponse {

    public final User user;

    public MyDataResponse(User user) {
        this.user = user;
    }

}
