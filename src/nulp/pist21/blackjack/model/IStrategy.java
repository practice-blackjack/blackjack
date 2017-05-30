package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.game.GameWithDealer;

public interface IStrategy {
    GameAction getGameAction(GameWithDealer game);
}
