package nulp.pist21.blackjack.model.game;

import nulp.pist21.blackjack.model.deck.Card;

public interface IHand {
    void giveCard(Card card);
    void takeCards();
    Card[] getHand();
}
