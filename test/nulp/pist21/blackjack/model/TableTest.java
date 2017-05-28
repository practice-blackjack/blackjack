package nulp.pist21.blackjack.model;

import org.junit.Assert;
import org.junit.Test;

public class TableTest {

    public Table createTable(){
        IDeck deck = new EndlessDeck();
        return new Table(0, "Kyiv", 100, 6, deck);
    }

    public void fillSomeBoxes(Table table){
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        table.getBoxes()[0].sitDown(user1);
        table.getBoxes()[2].sitDown(user2);
        table.getBoxes()[5].sitDown(user3);
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

        User user = new User();
        table.addSpectator(user);

        if (table.getSpectators().size() != 1) Assert.fail();
        if (table.getSpectators().get(0) != user) Assert.fail();
    }

    @Test
    public void should_remove_player_from_spectators_after_stand_up(){
        Table table = createTable();

        User user = new User();
        table.addSpectator(user);

        table.removePlayer(user);
        if (table.getSpectators().size() != 0) Assert.fail();
    }

    @Test
    public void should_give_first_cards(){
        Table table = (Table)createTable();
        fillSomeBoxes(table);

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
        fillSomeBoxes(table);

        table.giveFirstCards();
        table.takeCards();
        for(TableBox box: table.getBoxes()){
            if (box.getHand().size() != 0) Assert.fail();
        }
    }
}
