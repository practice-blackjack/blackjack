package nulp.pist21.blackjack.model.managers;

import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.Dealer;
import nulp.pist21.blackjack.model.Hand;
import org.junit.Assert;
import org.junit.Test;
import nulp.pist21.blackjack.model.managers.PlayManager.Actions;

public class PlayManagerTest {
    @Test
    public void should_give_first_cards(){
        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        PlayManager play = new PlayManager(deck, dealer);
        play.start(6);

        for(Hand hand: play.getHands()){
            Assert.assertEquals(2, hand.getHand().length);
        }
        Assert.assertEquals(2, dealer.getHand().length);
    }

    @Test
    public void should_go_to_next_if_a_lot(){
        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        PlayManager play = new PlayManager(deck, dealer);
        play.start(3);

        play.getHands()[1] = new Hand();
        play.getHands()[1].giveCard(new Card(Card.CLUBS, Card._10));
        play.getHands()[1].giveCard(new Card(Card.CLUBS, Card._10));
        play.getHands()[1].giveCard(new Card(Card.CLUBS, Card._10));

        play.getHands()[2] = new Hand();
        play.getHands()[2].giveCard(new Card(Card.CLUBS, Card._10));
        play.getHands()[2].giveCard(new Card(Card.CLUBS, Card._3));
        play.getHands()[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));

        play.next(Actions.STAND);

        Assert.assertTrue(play.isOver());
    }

    @Test
    public void should_go_to_next_if_21(){
        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        PlayManager play = new PlayManager(deck, dealer);
        play.start(3);

        play.getHands()[1] = new Hand();
        play.getHands()[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        play.getHands()[1].giveCard(new Card(Card.CLUBS, Card._10));
        play.getHands()[1].giveCard(new Card(Card.CLUBS, Card._10));

        play.getHands()[2] = new Hand();
        play.getHands()[2].giveCard(new Card(Card.CLUBS, Card._7));
        play.getHands()[2].giveCard(new Card(Card.CLUBS, Card._5));
        play.getHands()[2].giveCard(new Card(Card.CLUBS, Card._9));

        play.next(Actions.STAND);

        Assert.assertTrue(play.isOver());
    }

    @Test
    public void should_go_to_next_if_blackjack(){
        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();


        PlayManager play = new PlayManager(deck, dealer);
        play.start(3);

        play.getHands()[1] = new Hand();
        play.getHands()[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        play.getHands()[1].giveCard(new Card(Card.CLUBS, Card._10));

        play.getHands()[2] = new Hand();
        play.getHands()[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));
        play.getHands()[2].giveCard(new Card(Card.CLUBS, Card.ACE));

        play.next(Actions.STAND);

        Assert.assertTrue(play.isOver());
    }
}
