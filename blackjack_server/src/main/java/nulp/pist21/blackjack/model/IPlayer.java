package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.actions.BetAction;
import nulp.pist21.blackjack.model.game.GameWithDealer;
import nulp.pist21.blackjack.model.table.TableBox;

public interface IPlayer extends IStrategy {

    int getMoney();
    boolean takeMoney(int money);
    boolean giveMoney(int money);
    BetAction getBetAction(GameWithDealer game, TableBox box);

}
