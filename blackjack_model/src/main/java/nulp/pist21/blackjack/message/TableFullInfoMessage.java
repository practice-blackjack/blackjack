package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.TableFullInfo;

public class TableFullInfoMessage extends Message {

    private TableFullInfo tableFullInfo;

    public TableFullInfoMessage() {
        this("", null);
    }

    public TableFullInfoMessage(String type, TableFullInfo tableFullInfo) {
        super(type);
        this.tableFullInfo = tableFullInfo;
    }

    public TableFullInfo getTableFullInfo() {
        return tableFullInfo;
    }

    public void setTableFullInfo(TableFullInfo tableFullInfo) {
        this.tableFullInfo = tableFullInfo;
    }

}