package mock;

import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.IDeck;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class DeckMock implements IDeck {

    public DeckMock(List<Card> cards) {
        this.cards = cards;
    }

    private List<Card> cards;

    @Override
    public void shuffle() {
        throw new NotImplementedException();
    }

    @Override
    public boolean hasNext() {
        return !cards.isEmpty();
    }

    @Override
    public Card next() {
        return cards.remove(cards.size() - 1);
    }

    @Override
    public int cardsLeft() {
        return cards.size();
    }
}
