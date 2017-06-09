package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.TableInfo;

public class UserActionMessage extends Message {

    private TableInfo tableInfo;
    private int place;
    private String action;
    private int bet;

    public UserActionMessage() {
        this("", null, 0, "", 0);
    }

    public UserActionMessage(String type, TableInfo tableInfo, int place, String action) {
        this(type, tableInfo, 0, action, 0);
    }

    public UserActionMessage(String type, TableInfo tableInfo, int place, String action, int bet) {
        super(type);
        this.tableInfo = tableInfo;
        this.place = place;
        this.action = action;
        this.bet = bet;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTable(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
