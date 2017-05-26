package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.TableInfo;

public class SelectTableMessage extends StringMessage {

    private TableInfo tableInfo;

    public SelectTableMessage() {
        super("");
        tableInfo = new TableInfo("", 0, 0, 0, 0);
    }

    public SelectTableMessage(String message, TableInfo tableInfo) {
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
