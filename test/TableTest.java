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
    public void instance_should_create(){
        ITable table = createTable();
    }

    @Test
    public void have_to_have_clear_listener_list_after_creating(){
        ITable table = createTable();
        if (table.getListeners() == null) Assert.fail();
        if (table.getListeners().size() > 0) Assert.fail();
    }

    @Test
    public void user_should_be_added_to_listeners(){
        ITable table = createTable();
        User user = new User();
        table.addUser(user);
        if (table.getListeners().size() != 1) Assert.fail();
        Player player = (Player)table.getListeners().get(0);
        if (player.getUser() != user) Assert.fail();
    }
}
