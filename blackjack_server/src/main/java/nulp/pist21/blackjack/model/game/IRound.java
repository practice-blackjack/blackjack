package nulp.pist21.blackjack.model.game;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.IDeck;

public interface IRound {
    void start();
    boolean next(GameAction action);
    void end();
    IHand getCurrentHand();
    IHand getHand(int index);
    int getHandCount();
}
