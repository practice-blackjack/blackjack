package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.TableInfo;

public class EntryTableRequest {

    public final TableInfo tableInfo;

    public EntryTableRequest(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

}
