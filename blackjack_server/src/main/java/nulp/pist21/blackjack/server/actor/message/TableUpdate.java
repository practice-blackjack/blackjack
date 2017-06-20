package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.TableFullInfo;

public class TableUpdate {

    public final TableFullInfo tableFullInfo;

    public TableUpdate(TableFullInfo tableFullInfo) {
        this.tableFullInfo = tableFullInfo;
    }

}
