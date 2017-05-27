package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.TableInfo;

public class TableSmallInfoMessage extends StringMessage {

    private TableInfo tableInfo;

    public TableSmallInfoMessage() {
        super("");
        tableInfo = new TableInfo("", 0, 0, 0, 0);
    }

    public TableSmallInfoMessage(String message, TableInfo tableInfo) {
        super(message);
        this.tableInfo = tableInfo;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

}
