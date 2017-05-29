package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.deck.Card;
import org.junit.Assert;
import org.junit.Test;

public class DealerTest {

    @Test
    public void should_return_hidden_and_shown_card(){
        Dealer dealer = new Dealer();
        dealer.giveCard(new Card(Card.CLUBS, Card._5));

        Assert.assertEquals(2, dealer.getHand().size());
        Assert.assertEquals(Card.HIDEN_CARD, dealer.getHand().get(0));

        Assert.assertEquals(Card.CLUBS, dealer.getHand().get(1).getSuit());
        Assert.assertEquals(Card._5, dealer.getHand().get(1).getValue());
    }

    @Test
    public void should_return_two_shown_cards(){
        Dealer dealer = new Dealer();
        dealer.giveCard(new Card(Card.CLUBS, Card._5));
        dealer.giveCard(new Card(Card.CLUBS, Card.ACE));

        Assert.assertEquals(2, dealer.getHand().size());
        Assert.assertNotEquals(Card.HIDEN_CARD, dealer.getHand().get(0));
        Assert.assertFalse(dealer.getHand().contains(Card.HIDEN_CARD));
    }
}
