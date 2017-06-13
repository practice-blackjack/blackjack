package nulp.pist21.blackjack.model.table;

import mock.UserMock;
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
    public void should_return_false_if_sit_on_occupied(){
        TableBox box = new TableBox();
        UserMock userMock = new UserMock();
        UserMock userMock2 = new UserMock();
        box.activate(userMock);

        Assert.assertFalse(box.activate(userMock2));
        Assert.assertTrue(box.isActivated());
        Assert.assertEquals(box.getPlayer(), userMock);
    }

    @Test
    public void should_return_false_try_stand_other_player(){
        TableBox box = new TableBox();
        UserMock userMock = new UserMock();
        UserMock userMock2 = new UserMock();
        box.activate(userMock);

        Assert.assertFalse(box.deactivate(userMock2));
        Assert.assertTrue(box.isActivated());
        Assert.assertEquals(box.getPlayer(), userMock);
    }

    @Test
    public void should_set_and_return_activating_status(){
        TableBox box = new TableBox();
        UserMock userMock = new UserMock();
        box.activate(userMock);
        Assert.assertTrue(box.isActivated());
        box.deactivate(userMock);
        Assert.assertFalse(box.isActivated());
    }

    @Test
    public void should_clear_hand_on_deactivating(){
        TableBox box = new TableBox();
        UserMock userMock = new UserMock();
        box.activate(userMock);
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.deactivate(userMock);
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
