package nulp.pist21.blackjack.model;

import java.util.*;

public class Deck implements AbstractDeck {

    private ByteDeck[] deck;
    private Random random;
    private int countDeck;

    public Deck(int countDeck) {
        random = new Random();
        this.countDeck = countDeck;
        deck = new ByteDeck[countDeck];
        for (int i = 0; i < countDeck; i++) {
            deck[i] = new ByteDeck();
        }
        shuffle();
    }

    @Override
    public void shuffle() {
        Arrays.stream(deck).forEach(ByteDeck::shuffle);
    }

    @Override
    public boolean hasNext() {
        return Arrays.stream(deck).map(ByteDeck::hasNext).reduce(false, (a, b) -> a || b);
    }

    @Override
    public Card next() {
        int num;
        do {
            num = random.nextInt(countDeck);
        } while (!deck[num].hasNext());
        return deck[num].next();
    }

    @Override
    public int cardsLeft() {
        return Arrays.stream(deck).map(ByteDeck::cardsLeft).reduce(0, (a, b) -> a + b);
    }

    private class ByteDeck implements AbstractDeck {

        private long deck;
        private int cardLeft;

        public ByteDeck() {
            shuffle();
        }

        @Override
        public void shuffle() {
            cardLeft = 52;
            deck = 0b1111111111111111111111111111111111111111111111111111L;
        }

        @Override
        public boolean hasNext() {
            return cardLeft != 0;
        }

        @Override
        public Card next() {
            long value = deck;
            int iteration = -1;
            for (int count = random.nextInt(cardLeft) + 1; count > 0; iteration++, value >>= 1) {
                if ((value & 1) == 1) {
                    count--;
                }
            }
            long card = (long) Math.pow(2L, iteration);
            deck &= ~card;
            cardLeft--;
            return new Card(iteration);
        }

        @Override
        public int cardsLeft() {
            return cardLeft;
        }

    }

}
