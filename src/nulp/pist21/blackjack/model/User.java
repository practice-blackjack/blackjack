package nulp.pist21.blackjack.model;

public class User implements IPlayer, ISpectator {

    private int money;

    public User() {
        this.money = 500;
    }

    public User(int money) {
        this.money = money;
    }

    @Override
    public int getMoney() {
        return 0;
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
