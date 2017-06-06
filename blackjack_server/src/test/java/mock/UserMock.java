package mock;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.game.GameWithDealer;
import nulp.pist21.blackjack.model.game.IGame;
import nulp.pist21.blackjack.model.game.IStrategy;

public class UserMock implements IStrategy {

    private int stopOn;

    public UserMock(int stopOn) {
        this.stopOn = stopOn;
    }

    @Override
    public GameAction doStep(IGame game, int index){
        if (GameWithDealer.Combination.getPoints(game.getPlayer(index)) >= stopOn){
            return new GameAction(GameAction.Actions.STAND);
        }
        return new GameAction(GameAction.Actions.HIT);
    }
}
