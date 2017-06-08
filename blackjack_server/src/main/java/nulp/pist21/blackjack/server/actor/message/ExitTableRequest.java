package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.TableInfo;

public class ExitTableRequest {

    public final TableInfo tableInfo;

    public ExitTableRequest(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

}