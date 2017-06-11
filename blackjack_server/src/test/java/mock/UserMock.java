package mock;

import nulp.pist21.blackjack.model.actions.Action;
import nulp.pist21.blackjack.model.actions.BetAction;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.game.calculating.Combination;
import nulp.pist21.blackjack.model.game.round.IGame;
import nulp.pist21.blackjack.model.table.Table;

public class UserMock {

    private int stopOn;
    private int bet;

    public UserMock(int stopOn, int bet) {
        this.stopOn = stopOn;
        this.bet = bet;
    }

    public UserMock(int stopOn) {
        this(stopOn, 100);
    }

    public Action doStep(IGame game){
        if (game.getCurrentBox().getBet() == 0){
            return new BetAction(bet);
        }

        if (new Combination(game.getCurrentBox()).getPoints() >= stopOn){
            return new GameAction(GameAction.Actions.STAND);
        }
        return new GameAction(GameAction.Actions.HIT);
    }
}
