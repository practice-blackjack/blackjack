package nulp.pist21.blackjack.model;

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
    }

    @Test
    public void player_should_sit_down(){
        TableBox box = new TableTest().createTable().getBoxes()[0];
        if (!box.isFree()) Assert.fail();
        IPlayer player = new Player(new User());
        box.sitDown(player);
        if (box.getPlayer() != player) Assert.fail();
    }

    @Test
    public void player_should_stand_up(){
        TableBox box = new TableTest().createTable().getBoxes()[0];
        IPlayer player = new Player(new User());
        box.sitDown(player);
        box.standUp(player);
        if (box.getPlayer() != null) Assert.fail();
    }
}
