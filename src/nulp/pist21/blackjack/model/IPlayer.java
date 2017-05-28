package nulp.pist21.blackjack.model;

public interface IPlayer {

    int getMoney();
    boolean takeMoney(int money);
    boolean giveMoney(int money);
    Action getAction();

}
