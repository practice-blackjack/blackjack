package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.Mock.UserMock;
import org.junit.Assert;
import org.junit.Test;

public class TableTest {

    public Table createTable(){
        IDeck deck = new EndlessDeck();
        return new Table(0, "Kyiv", 100, 6, deck);
    }

    public UserMock[] fillSomeBoxes(Table table){
        UserMock user1 = new UserMock(500);
        UserMock user2 = new UserMock(700);
        UserMock user3 = new UserMock(1000);

        table.getBoxes()[0].sitDown(user1);
        table.getBoxes()[2].sitDown(user2);
        table.getBoxes()[5].sitDown(user3);

        return new UserMock[]{user1, user2, user3};
    }

    @Test
    public void should_have_clear_listener_list_after_creating(){
        Table table = createTable();
        if (table.getSpectators() == null) Assert.fail();
        if (table.getSpectators().size() > 0) Assert.fail();
    }

    @Test
    public void should_create_clear_boxes(){
        Table table = createTable();
        if (table.getBoxes() == null) Assert.fail();
        if (table.getBoxes().length != 6) Assert.fail();
        for (TableBox box: table.getBoxes()) {
            if (box == null) Assert.fail();
        }
    }

    @Test
    public void should_add_user_to_spectators(){
        Table table = createTable();

        UserMock user = new UserMock();
        table.addSpectator(user);

        if (table.getSpectators().size() != 1) Assert.fail();
        if (table.getSpectators().get(0) != user) Assert.fail();
    }

    @Test
    public void should_remove_player_from_spectators_after_stand_up(){
        Table table = createTable();

        UserMock user = new UserMock();
        table.addSpectator(user);

        table.removePlayer(user);
        if (table.getSpectators().size() != 0) Assert.fail();
    }

    @Test
    public void should_take_bets(){
        IDeck deck = new EndlessDeck();
        Table table = new Table(0, "Kyiv", 700, 6, deck);
        UserMock[] users = fillSomeBoxes(table);

        table.takeBets();

        if (users[0].getMoney() != 500) Assert.fail();
        if (table.getBoxes()[0].isInGame()) Assert.fail();

        if (users[1].getMoney() != 0) Assert.fail();
        if (users[2].getMoney() != 300) Assert.fail();
    }

    /*@Test
    public void should_pay_winners(){
        IDeck deck = new EndlessDeck();
        Table table = new Table(0, "Kyiv", 300, 6, deck);
        UserMock[] users = fillSomeBoxes(table);    //500 700 1000
        table.takeBets();

        table.getDealerBox().giveCard(new Card(Card.CLUBS, Card.ACE));
        table.getDealerBox().giveCard(new Card(Card.CLUBS, Card._5));       //16

        table.getBoxes()[0].giveCard(new Card(Card.CLUBS, Card._5));        //5

        table.getBoxes()[2].giveCard(new Card(Card.CLUBS, Card.ACE));
        table.getBoxes()[2].giveCard(new Card(Card.CLUBS, Card._5));        //16

        table.getBoxes()[5].giveCard(new Card(Card.CLUBS, Card.ACE));
        table.getBoxes()[5].giveCard(new Card(Card.CLUBS, Card._7));        //18

        table.endRound();

        if (users[0].getMoney() != 200) Assert.fail(users[0].getMoney() + ""); //lose
        if (users[1].getMoney() != 700) Assert.fail(users[1].getMoney() + ""); //draw
        if (users[2].getMoney() != 1300) Assert.fail(users[2].getMoney() + ""); //win
    }*/

    @Test
    public void should_clear_bets_on_end_of_round() {
        IDeck deck = new EndlessDeck();
        Table table = new Table(0, "Kyiv", 300, 6, deck);
        UserMock[] users = fillSomeBoxes(table);    //500 700 1000
        table.takeBets();
        table.endRound();
        for (TableBox box: table.getBoxes()){
            if (box.getBet() != 0) Assert.fail();
        }
    }

    @Test
    public void should_take_cards_on_end_of_round() {
        IDeck deck = new EndlessDeck();
        Table table = new Table(0, "Kyiv", 300, 6, deck);
        UserMock[] users = fillSomeBoxes(table);    //500 700 1000
        table.takeBets();
        table.endRound();
        for (TableBox box: table.getBoxes()){
            if (box.getHand().size() != 0) Assert.fail();
        }
    }
}
