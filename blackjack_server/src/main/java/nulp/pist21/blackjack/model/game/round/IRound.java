package nulp.pist21.blackjack.model.game.round;

import nulp.pist21.blackjack.model.actions.Action;

public interface IRound {
    boolean next(Action action); //return false if not handled
    int getIndex();
    void end();
    boolean isEnd();
}
