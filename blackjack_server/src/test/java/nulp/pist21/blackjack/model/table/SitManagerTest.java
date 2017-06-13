package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.Sit;
import nulp.pist21.blackjack.model.managers.SitManager;
import org.junit.Assert;
import org.junit.Test;

public class SitManagerTest {

    @Test
    public void should_create_clear_boxes_and_return_them(){
        SitManager sitManager = new SitManager(6);
        Assert.assertEquals(6, sitManager.getBoxes().length);
        for (int i = 0; i < sitManager.getBoxes().length; i++) {
            Assert.assertNotNull(sitManager.getBoxes()[i]);
        }
    }

    @Test
    public void should_return_active_boxes(){
        SitManager sitManager = new SitManager(6);
        sitManager.getBoxes()[0].activate();
        sitManager.getBoxes()[2].activate();
        sitManager.getBoxes()[5].activate();

        Sit playingBoxes[] = sitManager.getPlayingBoxes();

        Assert.assertEquals(3, playingBoxes.length);
        Assert.assertEquals(sitManager.getBoxes()[0], playingBoxes[0]);
        Assert.assertEquals(sitManager.getBoxes()[2], playingBoxes[1]);
        Assert.assertEquals(sitManager.getBoxes()[5], playingBoxes[2]);
    }
}
