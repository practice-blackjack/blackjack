package nulp.pist21.blackjack.model.table.game;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.deck.IDeck;

public interface IGame {
    void start(IHand[] playingBoxes, IDeck deck);
    boolean next(GameAction action, IDeck deck);
    void end();
    int getCurrentIndex();
    IHand getPlayer(int index);
    int getPlayerCount();
}
