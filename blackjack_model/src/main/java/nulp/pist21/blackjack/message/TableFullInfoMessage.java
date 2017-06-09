package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.table.Table;

public class TableFullInfoMessage extends Message {

    private Table table;

    public TableFullInfoMessage() {
        this("", null);
    }

    public TableFullInfoMessage(String type, Table table) {
        super(type);
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

}