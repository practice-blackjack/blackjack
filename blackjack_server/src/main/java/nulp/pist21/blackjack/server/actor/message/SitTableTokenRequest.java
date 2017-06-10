package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.TableInfo;

public class SitTableTokenRequest {

    public final TableInfo tableInfo;
    public final int place;
    public final long token;

    public SitTableTokenRequest(TableInfo tableInfo, int place, long token) {
        this.tableInfo = tableInfo;
        this.place = place;
        this.token = token;
    }

}
