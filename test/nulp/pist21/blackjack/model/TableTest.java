package nulp.pist21.blackjack.model;

import org.junit.Assert;
import org.junit.Test;

public class TableTest {

    public ITable createTable(){
        IDeck deck = new EndlessDeck();
        return new Table(0, "Kyiv", 100, 6, deck);
    }

    @Test
    public void should_create_instance(){
        ITable table = createTable();
    }

    @Test
    public void should_have_clear_listener_list_after_creating(){
        ITable table = createTable();
        if (table.getListeners() == null) Assert.fail();
        if (table.getListeners().size() > 0) Assert.fail();
    }

    @Test
    public void should_create_clear_boxes(){
        ITable table = createTable();
        if (table.getBoxes() == null) Assert.fail();
        if (table.getBoxes().length != 6) Assert.fail();
        for (TableBox box: table.getBoxes()) {
            if (box == null) Assert.fail();
        }
    }

    @Test
    public void should_add_user_to_listeners(){
        ITable table = createTable();

        User user = new User();
        Player player = table.addUser(user);

        if (table.getListeners().size() != 1) Assert.fail();
        if (player.getUser() != user) Assert.fail();
    }

    @Test
    public void should_return_right_player_after_adding_to_listeners(){
        ITable table = createTable();

        User user = new User();
        Player player = table.addUser(user);
        if (player != table.getListeners().get(0)) Assert.fail();
    }

    @Test
    public void should_remove_player_from_listeners_after_stand_up(){
        ITable table = createTable();

        User user = new User();
        Player player = table.addUser(user);

        table.removePlayer(player);
        if (table.getListeners().size() != 0) Assert.fail();

    }

    @Test
    public void should_give_first_cards(){
        Table table = (Table)createTable();

        User user1 = new User();
        Player player1 = table.addUser(user1);
        table.getBoxes()[0].sitDown(player1);

        User user2 = new User();
        Player player2 = table.addUser(user2);
        table.getBoxes()[2].sitDown(player2);

        User user3 = new User();
        Player player3 = table.addUser(user3);
        table.getBoxes()[5].sitDown(player3);

        table.giveFirstCards();

        if (table.getBoxes()[0].getHand().size() != 2) Assert.fail();
        if (table.getBoxes()[1].getHand().size() != 0) Assert.fail();
        if (table.getBoxes()[2].getHand().size() != 2) Assert.fail();
        if (table.getBoxes()[3].getHand().size() != 0) Assert.fail();
        if (table.getBoxes()[4].getHand().size() != 0) Assert.fail();
        if (table.getBoxes()[5].getHand().size() != 2) Assert.fail();
    }

    @Test
    public void should_take_cards(){
        Table table = (Table)createTable();

        User user1 = new User();
        Player player1 = table.addUser(user1);
        table.getBoxes()[0].sitDown(player1);

        User user2 = new User();
        Player player2 = table.addUser(user2);
        table.getBoxes()[2].sitDown(player2);

        User user3 = new User();
        Player player3 = table.addUser(user3);
        table.getBoxes()[5].sitDown(player3);

        table.giveFirstCards();
        table.takeCards();
        for(TableBox box: table.getBoxes()){
            if (box.getHand().size() != 0) Assert.fail();
        }
    }
}
