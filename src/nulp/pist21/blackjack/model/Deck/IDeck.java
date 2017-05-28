package nulp.pist21.blackjack.model.Deck;

import nulp.pist21.blackjack.model.Deck.Card;

public interface IDeck {

    void shuffle();

    boolean hasNext();

    Card next();

    int cardsLeft();

}
