import nulp.pist21.blackjack.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TableBoxSingleTest {

    @Test
    public void should_be_empty_after_creating() {
        TableBox box = new TableBox();
        if (!box.hasPlaces()) Assert.fail();
    }

    @Test
    public void player_should_sit_down(){
        TableBox box = new TableTest().createTable().getBoxes()[0];
        if (!box.hasPlaces()) Assert.fail();
        IPlayer player = new Player(new User());
        box.sitDown(player);
        if (box.getPlayers().length != 1) Assert.fail();
        if (box.getPlayers()[0] != player) Assert.fail();
    }

    @Test
    public void player_should_stand_up(){
        TableBox box = new TableTest().createTable().getBoxes()[0];
        IPlayer player = new Player(new User());
        box.sitDown(player);
        box.standUp(player);
        if (box.getPlayers().length != 0) Assert.fail();
    }
}
