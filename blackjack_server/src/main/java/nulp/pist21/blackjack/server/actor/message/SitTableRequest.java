package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.TableInfo;

public class SitTableRequest {

    public final TableInfo tableInfo;
    public final int place;

    public SitTableRequest(TableInfo tableInfo, int place) {
        this.tableInfo = tableInfo;
        this.place = place;
    }

}
