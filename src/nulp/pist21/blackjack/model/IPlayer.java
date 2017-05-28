package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.Actions.BetAction;
import nulp.pist21.blackjack.model.Table.TableBox;

public interface IPlayer extends IStrategy {

    int getMoney();
    boolean takeMoney(int money);
    boolean giveMoney(int money);
    BetAction getBetAction(GameWithDealer game, TableBox box);

}
