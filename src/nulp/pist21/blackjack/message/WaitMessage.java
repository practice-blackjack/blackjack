package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.TableInfo;

public class WaitMessage extends Message {

    private TableInfo tableInfo;

    public WaitMessage() {
        this("", null);
    }

    public WaitMessage(String type, TableInfo tableInfo) {
        super(type);
        this.tableInfo = tableInfo;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

}
