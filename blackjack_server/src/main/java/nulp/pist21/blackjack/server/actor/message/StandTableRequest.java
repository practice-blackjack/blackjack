package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.TableInfo;

public class StandTableRequest {

    public final TableInfo tableInfo;
    public final int place;

    public StandTableRequest(TableInfo tableInfo, int place) {
        this.tableInfo = tableInfo;
        this.place = place;
    }

}
