package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.TableInfo;

public class WaitMessage extends Message {

    private TableInfo tableInfo;
    private int place;

    public WaitMessage() {
        this("", null, 0);
    }

    public WaitMessage(String type, TableInfo tableInfo, int place) {
        super(type);
        this.tableInfo = tableInfo;
        this.place = place;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

}

