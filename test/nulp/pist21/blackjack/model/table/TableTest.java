package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.table.deck.EndlessDeck;
import nulp.pist21.blackjack.model.table.deck.IDeck;
import nulp.pist21.blackjack.model.table.game.GameWithDealer;
import org.junit.Assert;
import org.junit.Test;

public class TableTest {

    public Table createTable(){
        IDeck deck = new EndlessDeck();
        GameWithDealer game = new GameWithDealer(deck);
        return new Table(100, 6, game);
    }

    /*public UserMock[] fillSomeBoxes(Table table){
        UserMock user1 = new UserMock(500);
        UserMock user2 = new UserMock(700);
        UserMock user3 = new UserMock(1000);

        table.getBoxes()[0].sitDown(user1);
        table.getBoxes()[2].sitDown(user2);
        table.getBoxes()[5].sitDown(user3);

        return new UserMock[]{user1, user2, user3};
    }*/

    /*@Test
    public void should_have_clear_listener_list_after_creating(){
        Table table = createTable();
        Assert.assertNotNull(table.getSpectators());
        Assert.assertEquals(0, table.getSpectators().size());
    }*/

    @Test
    public void should_create_clear_boxes(){
        Table table = createTable();
        if (table.getBoxes() == null) Assert.fail();
        if (table.getBoxes().length != 6) Assert.fail();
        for (TableBox box: table.getBoxes()) {
            Assert.assertNotNull(box);
        }
    }

    /*@Test
    public void should_add_user_to_spectators(){
        Table table = createTable();

        UserMock user = new UserMock();
        table.addSpectator(user);

        Assert.assertEquals(1, table.getSpectators().size());
        Assert.assertEquals(user, table.getSpectators().get(0));
    }*/

   /* @Test
    public void should_remove_player_from_spectators_after_stand_up(){
        Table table = createTable();

        UserMock user = new UserMock();
        table.addSpectator(user);

        table.removePlayer(user);
        Assert.assertEquals(0, table.getSpectators().size());
    }*/

    /*@Test
    public void should_sit_down() {
        IDeck deck = new EndlessDeck();
        Table table = new Table(300, 6, deck);
        UserMock[] users = fillSomeBoxes(table);    //500 700 1000
        //Assert.assertTrue();
    }*/

    /*@Test
    public void should_take_bets(){
        IDeck deck = new EndlessDeck();
        Table table = new Table(700, 6, deck);
        UserMock[] users = fillSomeBoxes(table);

        table.takeBets();

        Assert.assertEquals(500, users[0].getMoney());
        Assert.assertEquals(0, users[1].getMoney());
        Assert.assertEquals(300, users[2].getMoney());

        Assert.assertFalse(table.getBoxes()[0].isInGame());
    }*/

    /*@Test
    public void should_clear_bets_on_end_of_round() {
        IDeck deck = new EndlessDeck();
        Table table = new Table(300, 6, deck);
        UserMock[] users = fillSomeBoxes(table);    //500 700 1000
        table.takeBets();
        table.endRound();
        for (TableBox box: table.getBoxes()){
            Assert.assertEquals(0, box.getBet());
        }
    }*/

    /*@Test
    public void should_take_cards_on_end_of_round() {
        IDeck deck = new EndlessDeck();
        Table table = new Table(300, 6, deck);
        UserMock[] users = fillSomeBoxes(table);    //500 700 1000
        table.takeBets();
        table.endRound();
        for (TableBox box: table.getBoxes()){
            Assert.assertEquals(0, box.getHand().size());
        }
    }*/
}
