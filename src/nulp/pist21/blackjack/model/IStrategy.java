package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.actions.GameAction;

public interface IStrategy {
    GameAction getGameAction(GameWithDealer game);
}
