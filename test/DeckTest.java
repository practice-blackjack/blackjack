import nulp.pist21.blackjack.model.*;
import org.junit.Assert;
import org.junit.Test;


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
}
