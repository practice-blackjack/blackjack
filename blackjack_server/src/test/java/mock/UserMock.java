package mock;

import nulp.pist21.blackjack.model.Player;
import nulp.pist21.blackjack.model.game.calculating.Combination;
import nulp.pist21.blackjack.model.game.managers.BetManager;
import nulp.pist21.blackjack.model.game.managers.PlayManager;

public class UserMock extends Player {

    private int stopOn;
    private int bet;

    public UserMock(int stopOn, int bet) {
        this.stopOn = stopOn;
        this.bet = bet;
    }

    public UserMock(int stopOn) {
        this(stopOn, 100);
    }

    public UserMock() {
        this(16, 100);
    }

    public int doBet(BetManager bets){
        if (bets.getCurrentBank().getBet() == 0){
            return bet;
        }
        return 0;
    }

    public PlayManager.Actions doStep(PlayManager play){
        if (new Combination(play.getCurrentHand()).getPoints() >= stopOn){
            return PlayManager.Actions.STAND;
        }
        return PlayManager.Actions.HIT;
    }
}
