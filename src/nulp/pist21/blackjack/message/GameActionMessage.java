package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.Table;

public class GameActionMessage extends StringMessage {

    public final static String HIT = "hit";
    public final static String STAND = "stand";
    public final static String BET = "bet";

    private Table table;
    private String action;
    private int bet;

    public GameActionMessage() {
        this("", null, "", 0);
    }

    public GameActionMessage(String message, Table table, String action) {
        this(message, table, action, 0);
    }

    public GameActionMessage(String message, Table table, String action, int bet) {
        super(message);
        this.table = table;
        this.action = action;
        this.bet = bet;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
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
