package nulp.pist21.blackjack.model.managers;

import nulp.pist21.blackjack.model.Sit;
import org.junit.Assert;
import org.junit.Test;

public class SitManagerTest {

    @Test
    public void should_create_clear_boxes_and_return_them(){
        SitManager sitManager = new SitManager(6);
        Assert.assertEquals(6, sitManager.getSits().length);
        for (int i = 0; i < sitManager.getSits().length; i++) {
            Assert.assertNotNull(sitManager.getSits()[i]);
        }
    }

    @Test
    public void should_return_active_boxes(){
        SitManager sitManager = new SitManager(6);
        sitManager.getSits()[0].sit();
        sitManager.getSits()[2].sit();
        sitManager.getSits()[5].sit();

        Sit playingBoxes[] = sitManager.getPlayingSits();

        Assert.assertEquals(3, playingBoxes.length);
        Assert.assertEquals(sitManager.getSits()[0], playingBoxes[0]);
        Assert.assertEquals(sitManager.getSits()[2], playingBoxes[1]);
        Assert.assertEquals(sitManager.getSits()[5], playingBoxes[2]);
    }
}
