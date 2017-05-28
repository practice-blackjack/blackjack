package nulp.pist21.blackjack.model;

public class Dealer implements IPlayer {

    @Override
    public int getMoney() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean takeMoney(int money) {
        return true;
    }

    @Override
    public boolean giveMoney(int money) {
        return true;
    }

    @Override
    public Action getAction() {
        return null;
    }

}
