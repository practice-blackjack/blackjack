package mock;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.game.Combination;
import nulp.pist21.blackjack.model.game.IRound;

public class UserMock {

    private int stopOn;

    public UserMock(int stopOn) {
        this.stopOn = stopOn;
    }

    public GameAction doStep(IRound round, int index){
        if (new Combination(round.getPlayer(index)).getPoints() >= stopOn){
            return new GameAction(GameAction.Actions.STAND);
        }
        return new GameAction(GameAction.Actions.HIT);
    }
}
