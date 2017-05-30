package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.table.deck.Card;

public interface IBox {
    Card[] getHand();
    void giveCard(Card card);

    void takeCards();
}
