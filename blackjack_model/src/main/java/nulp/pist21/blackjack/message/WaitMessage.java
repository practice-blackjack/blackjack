package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.Table;

public class WaitMessage extends StringMessage {

    private Table table;

    public WaitMessage() {
        super("");
        table = null;
    }

    public WaitMessage(String message, Table table) {
        super(message);
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

}
