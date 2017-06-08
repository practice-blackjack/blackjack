package nulp.pist21.blackjack.model.game;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.IDeck;

public interface IRound {
    void start();
    boolean next(GameAction action);
    void end();
    int getCurrentIndex();
    IHand getPlayer(int index);
    int getPlayerCount();
}
