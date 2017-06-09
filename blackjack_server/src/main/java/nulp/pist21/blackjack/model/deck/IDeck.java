package nulp.pist21.blackjack.model.deck;

public interface IDeck {

    void shuffle();

    boolean hasNext();

    Card next();

    int cardsLeft();

}
