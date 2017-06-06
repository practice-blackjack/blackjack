package nulp.pist21.blackjack.model.game;

import nulp.pist21.blackjack.model.actions.GameAction;

public interface IStrategy {
    GameAction doStep(IGame game, int index);
}
