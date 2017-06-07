package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.deck.Card;
import org.junit.Assert;
import org.junit.Test;


public class TableBoxTest {

    @Test
    public void should_have_empty_hand_on_creating() {
        TableBox box = new TableBox();
        Assert.assertEquals(0, box.getHand().length);
    }

    @Test
    public void should_be_deactivated_on_creating(){
        TableBox box = new TableBox();
        Assert.assertFalse(box.isActivated());
    }

    @Test
    public void should_set_and_return_activating_status(){
        TableBox box = new TableBox();
        box.isActivated(true);
        Assert.assertTrue(box.isActivated());
        box.isActivated(false);
        Assert.assertFalse(box.isActivated());
    }

    @Test
    public void should_clear_hand_on_deactivating(){
        TableBox box = new TableBox();
        box.isActivated(true);
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.isActivated(false);
        Assert.assertEquals(0, box.getHand().length);
    }

    @Test
    public void should_give_cards(){
        TableBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        Assert.assertEquals(2, box.getHand().length);
    }

    @Test
    public void should_take_cards(){
        TableBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        box.takeCards();
        Assert.assertEquals(0, box.getHand().length);
    }

    @Test
    public void should_return_cards(){
        TableBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.SPADES, Card._7));

        Assert.assertEquals(Card.CLUBS, box.getHand()[0].getSuit());
        Assert.assertEquals(Card.ACE, box.getHand()[0].getValue());

        Assert.assertEquals(Card.SPADES, box.getHand()[1].getSuit());
        Assert.assertEquals(Card._7, box.getHand()[1].getValue());
    }

}
