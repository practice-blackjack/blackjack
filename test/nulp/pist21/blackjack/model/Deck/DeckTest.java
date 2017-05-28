package nulp.pist21.blackjack.model.Deck;

import nulp.pist21.blackjack.model.Deck.Card;
import nulp.pist21.blackjack.model.Deck.Deck;
import nulp.pist21.blackjack.model.Deck.IDeck;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class DeckTest {
    @Test
    public void should_return_right_cards_count_after_creating(){
        for (int i = 1; i <= 10; i++){
            IDeck deck = new Deck(i);
            if (deck.cardsLeft() != 52 * i) Assert.fail();
        }
    }

    @Test
    public void should_return_right_cards_count_after_pulling(){
        IDeck deck = new Deck(1);

        for (int i = deck.cardsLeft() - 1; i >= 0; i--){
            Card card = deck.next();
            if (i != deck.cardsLeft()) Assert.fail();
        }
        if (deck.hasNext()) Assert.fail();
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
                if (card1.getSuit() == card2.getSuit() &&
                    card1.getValue() == card2.getValue()) Assert.fail();
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

        if (deck.cardsLeft() != 0) Assert.fail("Test works incorrect. Deck is not empty");
        deck.next();

        if (deck.cardsLeft() != deckSize - 1) Assert.fail();
    }

}
