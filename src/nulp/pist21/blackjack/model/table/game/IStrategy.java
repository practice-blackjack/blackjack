package nulp.pist21.blackjack.model.table.game;

import nulp.pist21.blackjack.model.actions.GameAction;

public interface IStrategy {
    public GameAction doStep(IGame game, int index);
}
