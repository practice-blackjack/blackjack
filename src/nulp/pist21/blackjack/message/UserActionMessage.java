package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.TableInfo;

public class UserActionMessage extends Message {

    public final static String HIT = "hit";
    public final static String STAND = "stand";
    public final static String BET = "bet";

    private TableInfo tableInfo;
    private String action;
    private int bet;

    public UserActionMessage() {
        this("", null, "", 0);
    }

    public UserActionMessage(String type, TableInfo tableInfo, String action) {
        this(type, tableInfo, action, 0);
    }

    public UserActionMessage(String type, TableInfo tableInfo, String action, int bet) {
        super(type);
        this.tableInfo = tableInfo;
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

}
