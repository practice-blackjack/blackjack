package nulp.pist21.blackjack.model.game;

import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.game.Dealer;
import nulp.pist21.blackjack.model.game.GameWithDealer;
import org.junit.Assert;
import org.junit.Test;

public class DealerTest {

    @Test
    public void should_hide_first_card(){
        Dealer dealer = new Dealer();
        dealer.giveCard(new Card(Card.CLUBS, Card._5));
        dealer.giveCard(new Card(Card.CLUBS, Card.ACE));

        Assert.assertEquals(2, dealer.getHand().size());
        Assert.assertEquals(Card.HIDEN_CARD, dealer.getHand().get(0));
    }

    @Test
    public void should_open_cards_on_dealers_step(){
        Dealer dealer = new Dealer();
        dealer.giveCard(new Card(Card.CLUBS, Card._5));
        dealer.giveCard(new Card(Card.CLUBS, Card.ACE));

        dealer.getGameAction(new GameWithDealer(dealer));

        Assert.assertFalse(dealer.getHand().contains(Card.HIDEN_CARD));
    }
}
