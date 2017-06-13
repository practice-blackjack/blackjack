package nulp.pist21.blackjack.model.table;

import mock.UserMock;
import org.junit.Assert;
import org.junit.Test;


public class SitTest {

    @Test
    public void should_be_deactivated_on_creating(){
        Sit box = new Sit();
        Assert.assertFalse(box.isActivated());
    }

    @Test
    public void should_return_false_if_sit_on_occupied(){
        Sit box = new Sit();
        UserMock userMock = new UserMock();
        UserMock userMock2 = new UserMock();
        box.activate(userMock);

        Assert.assertFalse(box.activate(userMock2));
        Assert.assertTrue(box.isActivated());
        Assert.assertEquals(box.getPlayer(), userMock);
    }

    @Test
    public void should_return_false_try_stand_other_player(){
        Sit box = new Sit();
        UserMock userMock = new UserMock();
        UserMock userMock2 = new UserMock();
        box.activate(userMock);

        Assert.assertFalse(box.deactivate(userMock2));
        Assert.assertTrue(box.isActivated());
        Assert.assertEquals(box.getPlayer(), userMock);
    }

    @Test
    public void should_set_and_return_activating_status(){
        Sit box = new Sit();
        UserMock userMock = new UserMock();
        box.activate(userMock);
        Assert.assertTrue(box.isActivated());
        box.deactivate(userMock);
        Assert.assertFalse(box.isActivated());
    }
}
