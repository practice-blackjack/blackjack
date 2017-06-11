package nulp.pist21.blackjack.model.game.round;

import nulp.pist21.blackjack.model.actions.Action;
import nulp.pist21.blackjack.model.actions.BetAction;
import nulp.pist21.blackjack.model.game.IBetable;

public class BetRound implements IRound {
    private IBetable players[];
    private int index;
    private int minBet;
    private int maxBet;

    public BetRound(IBetable[] players, int minBet, int maxBet) {
        this.players = players;
        this.minBet = minBet;
        this.maxBet = maxBet;
    }

    @Override
    public boolean next(Action action) {
        if (index >= players.length) {
            return false;
        }
        if (!(action instanceof BetAction)){
            return false;
        }
        BetAction betAction = (BetAction) action;
        if (betAction.getBet() < minBet || betAction.getBet() > maxBet){
            return false;
        }
        players[index].setBet(betAction.getBet());
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
