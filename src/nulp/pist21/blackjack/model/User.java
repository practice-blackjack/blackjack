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
    public Action getAction() {
        return null;
    }

    @Override
    public void sendData(Table table) {

    }
}
