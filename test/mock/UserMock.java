package mock;

import nulp.pist21.blackjack.model.*;
import nulp.pist21.blackjack.model.actions.BetAction;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.game.GameWithDealer;
import nulp.pist21.blackjack.model.table.Table;
import nulp.pist21.blackjack.model.table.TableBox;

public class UserMock {

    private int stopOn;

    public UserMock(int stopOn) {
        this.stopOn = stopOn;
    }

    public GameAction doStep(GameWithDealer game, int index){
        if (game.getValue(index) >= stopOn){
            return new GameAction(GameAction.Actions.STAND);
        }
        return new GameAction(GameAction.Actions.HIT);
    }
}
