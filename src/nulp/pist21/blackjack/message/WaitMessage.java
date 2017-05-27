package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.TableInfo;

public class WaitMessage extends Message {

    public static final String WAIT_BET = "bet";
    public static final String WAIT_HIT_OR_STAND = "hit_or_stand";

    private TableInfo tableInfo;
    private int place;
    private String waitType;

    public WaitMessage() {
        this("", null, 0, WAIT_BET);
    }

    public WaitMessage(String type, TableInfo tableInfo, int place, String waitType) {
        super(type);
        this.tableInfo = tableInfo;
        this.place = place;
        this.waitType = waitType;
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

    public String getWaitType() {
        return waitType;
    }

    public void setWaitType(String waitType) {
        this.waitType = waitType;
    }

}
