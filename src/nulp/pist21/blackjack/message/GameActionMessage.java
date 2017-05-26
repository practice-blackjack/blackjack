package nulp.pist21.blackjack.message;

public class GameActionMessage extends StringMessage {

    public final static String HIT = "hit";
    public final static String STAND = "stand";
    public final static String BET = "bet";

    private String action;

    private int bet;

    public GameActionMessage() {
        this("", "", 0);
    }

    public GameActionMessage(String message, String action) {
        this(message, action, 0);
    }

    public GameActionMessage(String message, String action, int bet) {
        super(message);
        this.action = action;
        this.bet = bet;
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