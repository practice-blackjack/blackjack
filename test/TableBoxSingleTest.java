import nulp.pist21.blackjack.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TableBoxSingleTest {
    private Table table;

    @Before
    public void before(){
        table = new TableTest().instance_should_create();
    }

    @Test
    public void should_be_empty_after_creating() {
        ITableBox box = new TableBoxSingle();
        if (!box.hasPlaces()) Assert.fail();
    }

    @Test
    public void player_should_sit(){
        ITableBox box = table.getBoxes()[0];
        if (!box.hasPlaces()) Assert.fail();
        IPlayer player = new Player();
        box.sit(player);
        if (box.getPlayers().length <= 0) Assert.fail();
    }

    @Test
    public void should_return_right_players_list(){
        ITableBox box = new TableBoxSingle();
        if (box.getPlayers().length != 0) Assert.fail();

        IPlayer player = new Player();
        box.sit(player);

        if (box.getPlayers().length != 1) Assert.fail();
        if (box.getPlayers()[0] != player) Assert.fail();

    }

}
