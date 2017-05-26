package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.Table;

public class TableMessage extends StringMessage {

    private Table table;

    public TableMessage() {
        super("");
        table = new Table();
    }

    public TableMessage(String message, Table table) {
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