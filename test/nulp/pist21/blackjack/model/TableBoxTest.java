package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.Mock.UserMock;
import org.junit.Assert;
import org.junit.Test;


public class TableBoxTest {

    @Test
    public void should_be_empty_after_creating() {
        TableBox box = new TableBox();
        if (!box.isFree()) Assert.fail();
    }

    @Test
    public void should_create_empty_hand() {
        TableBox box = new TableBox();
        if (box.getHand() == null) Assert.fail();
        if (box.getHand().size() != 0) Assert.fail();
        if (box.getValue() != 0) Assert.fail();
    }

    @Test
    public void player_should_sit_down(){
        TableBox box = new TableTest().createTable().getBoxes()[0];
        if (!box.isFree()) Assert.fail();
        UserMock user = new UserMock();
        box.sitDown(user);
        if (box.getPlayer() != user) Assert.fail();
    }

    @Test
    public void player_should_stand_up(){
        TableBox box = new TableTest().createTable().getBoxes()[0];
        UserMock user = new UserMock();
        box.sitDown(user);
        box.makeFree();
        if (box.getPlayer() != null) Assert.fail();
    }

    @Test
    public void should_give_cards(){
        TableBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        if (box.getHand().size() != 2) Assert.fail();
    }

    @Test
    public void should_take_cards(){
        TableBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        box.takeCards();
        if (box.getHand().size() != 0) Assert.fail();
    }

    @Test
    public void should_return_cards_value(){
        TableBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        if (box.getValue() != 18) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card._8));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        if (box.getValue() != 15) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        if (box.getValue() != 19) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._8));
        if (box.getValue() != 17) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card.JACK));
        if (box.getValue() != 21) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card.QUEEN));
        if (box.getValue() != 21) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card.KING));
        if (box.getValue() != 21) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        if (box.getValue() != 12) Assert.fail();
    }

    @Test
    public void should_take_bet(){
        UserMock user = new UserMock(100);
        TableBox box = new TableBox();
        box.sitDown(user);
        box.takeBet(5);

        if (user.getMoney() != 95) Assert.fail();

    }

    @Test
    public void should_remove_player_if_cant_pay(){
        UserMock user = new UserMock(100);
        TableBox box = new TableBox();
        box.sitDown(user);
        box.takeBet(200);

        if (!box.isFree()) Assert.fail();
        if (user.getMoney() != 100) Assert.fail();
    }
}
