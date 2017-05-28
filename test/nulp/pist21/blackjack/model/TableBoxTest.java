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
        User user = new User();
        box.sitDown(user);
        if (box.getPlayer() != user) Assert.fail();
    }

    @Test
    public void player_should_stand_up(){
        TableBox box = new TableTest().createTable().getBoxes()[0];
        User user = new User();
        box.sitDown(user);
        box.makeFree();
        if (box.getPlayer() != null) Assert.fail();
    }

    @Test
    public void should_return_cards_value(){

    }

    @Test
    public void should_remove_player_if_cant_pay(){

    }
}
