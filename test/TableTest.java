import nulp.pist21.blackjack.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TableTest {

    public ITable createTable(){
        ITableBox[] boxes = {
                new TableBoxSingle(),
                new TableBoxSingle(),
                new TableBoxSingle(),
                new TableBoxSingle(),
                new TableBoxSingle()};
        IDeck deck = new EndlessDeck();
        return new Table("Kyiv", 100, boxes, deck);
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
}
