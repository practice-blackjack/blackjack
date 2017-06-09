package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.table.Table;

public class TableUpdate {

    public final Table table;

    public TableUpdate(Table table) {
        this.table = table;
    }

}
