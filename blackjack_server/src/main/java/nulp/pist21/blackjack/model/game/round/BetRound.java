package nulp.pist21.blackjack.model.game.round;

import nulp.pist21.blackjack.model.actions.Action;
import nulp.pist21.blackjack.model.actions.BetAction;
import nulp.pist21.blackjack.model.game.IBetable;

public class BetRound implements IRound {
    private IBetable players[];
    private int index;

    public BetRound(IBetable[] players) {
        this.players = players;
    }

    @Override
    public boolean next(Action action) {
        if (index >= players.length) {
            return false;
        }
        if (!(action instanceof BetAction)){
            return false;
        }
        players[index].setBet(((BetAction) action).getBet());
        index++;
        return true;
    }

    @Override
    public void end() {
        for (IBetable player: players) {
            player.setBet(0);
        }
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public boolean isEnd() {
        return index >= players.length;
    }
}
