package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.Dealer;
import nulp.pist21.blackjack.model.Hand;
import nulp.pist21.blackjack.model.deck.Card;
import org.junit.Assert;
import org.junit.Test;

public class DealerTest {

    @Test
    public void should_hide_first_card(){
        Dealer dealer = new Dealer();
        dealer.giveCard(new Card(Card.CLUBS, Card._6));
        dealer.giveCard(new Card(Card.DIAMONDS, Card._10));

        Assert.assertEquals(Card.UNDEFINED_SUIT, dealer.getHand()[0].getSuit());
        Assert.assertEquals(Card.UNDEFINED_VALUE, dealer.getHand()[0].getValue());

        Assert.assertEquals(Card.DIAMONDS, dealer.getHand()[1].getSuit());
        Assert.assertEquals(Card._10, dealer.getHand()[1].getValue());
    }

    @Test
    public void should_open_hidden_card_on_step(){
        Dealer dealer = new Dealer();
        dealer.giveCard(new Card(Card.CLUBS, Card._6));
        dealer.giveCard(new Card(Card.DIAMONDS, Card._10));

        dealer.doStep(new Hand[0]);

        Assert.assertEquals(Card.CLUBS, dealer.getHand()[0].getSuit());
        Assert.assertEquals(Card._6, dealer.getHand()[0].getValue());

        Assert.assertEquals(Card.DIAMONDS, dealer.getHand()[1].getSuit());
        Assert.assertEquals(Card._10, dealer.getHand()[1].getValue());
    }
}
