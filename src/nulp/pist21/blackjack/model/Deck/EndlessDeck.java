package nulp.pist21.blackjack.model.Deck;

import nulp.pist21.blackjack.model.Deck.Card;
import nulp.pist21.blackjack.model.Deck.IDeck;

import java.util.Random;

public class EndlessDeck implements IDeck {

    private final Random random;

    public EndlessDeck() {
        random = new Random();
    }

    @Override
    public void shuffle() {
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Card next() {
        return new Card(random.nextInt(4), random.nextInt(13));
    }

    @Override
    public int cardsLeft() {
        return Integer.MAX_VALUE;
    }

}
