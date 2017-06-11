package nulp.pist21.blackjack.model.game.round;

import nulp.pist21.blackjack.model.table.TableBox;

public interface IGame {
    IRound getCurrentRound();
    boolean isOver();
    void start();
    int getCurrentIndex();
    TableBox getCurrentBox();
}
