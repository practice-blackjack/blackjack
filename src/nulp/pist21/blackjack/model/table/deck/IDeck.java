package nulp.pist21.blackjack.model.table.deck;

public interface IDeck {

    void shuffle();

    boolean hasNext();

    Card next();

    int cardsLeft();

}
