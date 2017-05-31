package mock;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.game.IGame;

public class UserMock {

    private int stopOn;

    public UserMock(int stopOn) {
        this.stopOn = stopOn;
    }

    public GameAction doStep(IGame game, int index){
        if (game.getValue(index) >= stopOn){
            return new GameAction(GameAction.Actions.STAND);
        }
        return new GameAction(GameAction.Actions.HIT);
    }
}
