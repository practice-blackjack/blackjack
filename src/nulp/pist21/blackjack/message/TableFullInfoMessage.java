package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.Table;

public class TableFullInfoMessage extends StringMessage {

    private Table table;

    public TableFullInfoMessage() {
        super("");
        table = new Table();
    }

    public TableFullInfoMessage(String message, Table table) {
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
