package nulp.pist21.blackjack.model.table.game;

import nulp.pist21.blackjack.model.table.DealerBox;
import nulp.pist21.blackjack.model.table.deck.Card;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class DealerBoxTest {

    @Test
    public void should_hide_first_card(){
        DealerBox dealerbox = new DealerBox();
        dealerbox.giveCard(new Card(Card.CLUBS, Card._5));
        dealerbox.giveCard(new Card(Card.CLUBS, Card.ACE));

        Assert.assertEquals(2, dealerbox.getHand().length);
        Assert.assertEquals(Card.HIDDEN_CARD, dealerbox.getHand()[0]);
    }

    @Test
    public void should_open_cards(){
        DealerBox dealerbox = new DealerBox();
        dealerbox.giveCard(new Card(Card.CLUBS, Card._5));
        dealerbox.giveCard(new Card(Card.CLUBS, Card.ACE));

        dealerbox.openCard();

        Assert.assertFalse(Arrays.stream(dealerbox.getHand()).anyMatch(c -> c == Card.HIDDEN_CARD));
    }
}
