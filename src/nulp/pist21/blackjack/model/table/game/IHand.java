package nulp.pist21.blackjack.model.table.game;

import nulp.pist21.blackjack.model.table.deck.Card;

public interface IHand {
    void giveCard(Card card);
    void takeCards();
    Card[] getHand();
}
