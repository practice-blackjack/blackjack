package nulp.pist21.blackjack.model.Mock;

import nulp.pist21.blackjack.model.Action;
import nulp.pist21.blackjack.model.IPlayer;
import nulp.pist21.blackjack.model.ISpectator;
import nulp.pist21.blackjack.model.Table;

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
        if (money > prevMoney) return true;
        this.money = prevMoney;
        return false;

    }

    @Override
    public Action getAction() {
        return null;
    }

    @Override
    public void sendData(Table table) {

    }
}
