package nulp.pist21.blackjack.model.managers;

import nulp.pist21.blackjack.model.Sit;
import org.junit.Assert;
import org.junit.Test;


public class SitTest {

    @Test
    public void should_be_deactivated_on_creating(){
        Sit box = new Sit();
        Assert.assertFalse(box.isActivated());
    }

    @Test
    public void should_return_false_if_sit_on_occupied_sit(){
        Sit box = new Sit();
        box.sit();

        Assert.assertFalse(box.sit());
        Assert.assertTrue(box.isActivated());
    }

    @Test
    public void should_return_false_on_stand_of_not_occupied(){
        Sit box = new Sit();
        Assert.assertFalse(box.stand());
    }

    @Test
    public void should_set_and_return_activating_status(){
        Sit box = new Sit();
        box.sit();
        Assert.assertTrue(box.isActivated());
        box.stand();
        Assert.assertFalse(box.isActivated());
    }
}
