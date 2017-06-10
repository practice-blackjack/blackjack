package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.User;

public class SitTableUserRequest {

    public final TableInfo tableInfo;
    public final int place;
    public final User user;

    public SitTableUserRequest(TableInfo tableInfo, int place, User user) {
        this.tableInfo = tableInfo;
        this.place = place;
        this.user = user;
    }

}
