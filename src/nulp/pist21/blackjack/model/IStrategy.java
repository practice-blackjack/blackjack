package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.Actions.GameAction;

public interface IStrategy {
    GameAction getGameAction(GameWithDealer game);
}
