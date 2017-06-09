package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.TableInfo;

public class WaitAction {

    public final TableInfo tableInfo;
    public final int place;
    public final String waitType;

    public WaitAction(TableInfo tableInfo, int place, String waitType) {
        this.tableInfo = tableInfo;
        this.place = place;
        this.waitType = waitType;
    }

}
