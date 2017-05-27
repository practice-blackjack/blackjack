package nulp.pist21.blackjack.model;

public interface IDeck {

    void shuffle();

    boolean hasNext();

    Card next();

    int cardsLeft();

}
