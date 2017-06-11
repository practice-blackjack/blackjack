package nulp.pist21.blackjack.model.actions;

public class BetAction implements Action {
    private int bet;

    public BetAction(int bet) {
        this.bet = bet;
    }

    public int getBet() {
        return bet;
    }

}
