package nulp.pist21.blackjack.model;

public interface AbstractDeck {

    void shuffle();

    boolean hasNext();

    Card next();

    int cardsLeft();

}
