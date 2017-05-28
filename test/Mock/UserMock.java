package Mock;

import nulp.pist21.blackjack.model.*;
import nulp.pist21.blackjack.model.Actions.BetAction;
import nulp.pist21.blackjack.model.Actions.GameAction;
import nulp.pist21.blackjack.model.Table.Table;
import nulp.pist21.blackjack.model.Table.TableBox;

public class UserMock implements IPlayer, ISpectator {

    private int money;

    public UserMock() {
        this.money = 500;
    }

    public UserMock(int money) {
        this.money = money;
    }

    @Override
    public int getMoney() {
        return money;
    }

    @Override
    public boolean takeMoney(int money) {
        if (this.money < money) return false;
        this.money -= money;
        return true;
    }

    @Override
    public boolean giveMoney(int money) {
        int prevMoney = this.money;
        this.money += money;
        if (this.money > prevMoney) return true;
        this.money = prevMoney;
        return false;

    }

    @Override
    public GameAction getGameAction(GameWithDealer game){
        return null;
    }

    @Override
    public BetAction getBetAction(GameWithDealer game, TableBox box){
        return null;
    }

    @Override
    public void sendData(Table table) {

    }
}
