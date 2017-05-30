package nulp.pist21.blackjack.model.table.deck;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class DeckTest {
    @Test
    public void should_return_right_cards_count_after_creating(){
        for (int i = 1; i <= 10; i++){
            IDeck deck = new Deck(i);
            Assert.assertEquals(52 * i, deck.cardsLeft());
        }
    }

    @Test
    public void should_return_right_cards_count_after_pulling(){
        IDeck deck = new Deck(1);

        for (int i = deck.cardsLeft() - 1; i >= 0; i--){
            Card card = deck.next();
            Assert.assertEquals(i, deck.cardsLeft());
        }
        Assert.assertFalse(deck.hasNext());
    }

    @Test
    public void should_not_have_duplicates(){
        IDeck deck = new Deck(1);
        List<Card> cards = new ArrayList<Card>();
        for (int i = deck.cardsLeft() - 1; i >= 0; i--){
            Card card = deck.next();
            cards.add(card);
        }
        for (int i = 0; i < cards.size() - 1; i++){
            Card card1 = cards.get(i);
            for (int j = i + 1; j < cards.size(); j++){
                Card card2 = cards.get(j);
                Assert.assertFalse(card1.getSuit() == card2.getSuit() &&
                    card1.getValue() == card2.getValue());
            }
        }
    }

    @Test
    public void should_shuffle_when_is_done(){
        IDeck deck = new Deck(1);
        int deckSize = deck.cardsLeft();
        for (int i = deck.cardsLeft() - 1; i >= 0; i--){
            deck.next();
        }
        Assert.assertEquals("Test works incorrect. deck is not empty", 0, deck.cardsLeft());
        deck.next();

        if (deck.cardsLeft() != deckSize - 1) Assert.fail();
    }

}
