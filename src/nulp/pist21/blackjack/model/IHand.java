package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.Deck.Card;

import java.util.List;

public interface IHand {
    List<Card> getHand();
    void giveCard(Card card);

    void takeCards();
}
