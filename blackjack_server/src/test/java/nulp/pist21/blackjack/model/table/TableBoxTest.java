package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.deck.Card;
import mock.UserMock;
import org.junit.Assert;
import org.junit.Test;


public class TableBoxTest {

    @Test
    public void should_be_empty_after_creating() {
        TableBox box = new TableBox();
        Assert.assertTrue(box.isFree());
    }

    @Test
    public void should_create_empty_hand() {
        TableBox box = new TableBox();
        Assert.assertNotNull(box.getHand());
        Assert.assertEquals(0, box.getHand().size());
    }

    @Test
    public void should_sit_player_down(){
        TableBox box = new TableTest().createTable().getBoxes()[0];
        Assert.assertTrue(box.isFree());
        UserMock user = new UserMock();

        Assert.assertTrue(box.sitDown(user));
        Assert.assertFalse(box.isFree());
        Assert.assertEquals(user, box.getPlayer());
    }

    @Test
    public void should_not_sit_player_if_occupied(){
        TableBox box = new TableTest().createTable().getBoxes()[0];
        Assert.assertTrue(box.isFree());
        UserMock user = new UserMock();

        box.sitDown(user);
        Assert.assertFalse(box.sitDown(user));
    }

    @Test
    public void should_stand_player_up(){
        TableBox box = new TableTest().createTable().getBoxes()[0];
        UserMock user = new UserMock();
        box.sitDown(user);
        Assert.assertTrue(box.standUp(user));
        Assert.assertTrue(box.isFree());
    }

    @Test
    public void should_not_stand_player_up_if_occupied_not_by_him(){
        TableBox box = new TableTest().createTable().getBoxes()[0];
        UserMock user = new UserMock();
        UserMock user2 = new UserMock();
        box.sitDown(user);
        Assert.assertFalse(box.standUp(user2));
        Assert.assertFalse(box.isFree());
    }

    @Test
    public void should_give_cards(){
        TableBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        Assert.assertEquals(2, box.getHand().size());
    }

    @Test
    public void should_take_cards(){
        TableBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        box.takeCards();
        Assert.assertEquals(0, box.getHand().size());
    }

    @Test
    public void should_take_bet(){
        UserMock user = new UserMock(100);
        TableBox box = new TableBox();
        box.sitDown(user);
        box.setBet(5);
        Assert.assertEquals(95, user.getMoney());
    }

    @Test
    public void should_remove_player_if_cant_pay(){
        UserMock user = new UserMock(100);
        TableBox box = new TableBox();
        box.sitDown(user);
        box.setBet(200);

        Assert.assertTrue(box.isFree());
        Assert.assertEquals(100, user.getMoney());
    }

}
