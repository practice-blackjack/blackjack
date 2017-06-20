package mock;

import nulp.pist21.blackjack.model.Player;
import nulp.pist21.blackjack.model.calculating.Combination;
import nulp.pist21.blackjack.model.managers.BetManager;
import nulp.pist21.blackjack.model.managers.PlayManager;

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
        int index = bets.getIndex();
        if (bets.getBanks()[index] == 0){
            return bet;
        }
        return 0;
    }

    public PlayManager.Actions doStep(PlayManager play){
        int index = play.getIndex();
        if (new Combination(play.getHands()[index]).getPoints() >= stopOn){
            return PlayManager.Actions.STAND;
        }
        return PlayManager.Actions.HIT;
    }
}
