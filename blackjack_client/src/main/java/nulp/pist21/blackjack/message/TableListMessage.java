package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.TableInfo;

import java.util.ArrayList;
import java.util.List;

public class TableListMessage extends StringMessage {

    private List<TableInfo> tableList;

    public TableListMessage() {
        super("");
        tableList = new ArrayList<>();
    }

    public TableListMessage(String message, List<TableInfo> tableList) {
        super(message);
        this.tableList = tableList;
    }

    public List<TableInfo> getTableList() {
        return tableList;
    }

    public void setTableList(List<TableInfo> tableList) {
        this.tableList = tableList;
    }

}