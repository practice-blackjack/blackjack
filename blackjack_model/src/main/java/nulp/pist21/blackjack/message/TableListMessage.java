package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.TableInfo;

import java.util.ArrayList;
import java.util.List;

public class TableListMessage extends Message {

    private List<TableInfo> tableList;

    public TableListMessage() {
        this("", null);
    }

    public TableListMessage(String type, List<TableInfo> tableList) {
        super(type);
        this.tableList = tableList;
    }

    public List<TableInfo> getTableList() {
        return tableList;
    }

    public void setTableList(List<TableInfo> tableList) {
        this.tableList = tableList;
    }

}