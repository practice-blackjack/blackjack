package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.Player;
import nulp.pist21.blackjack.model.managers.PlayerManager;
import org.junit.Assert;
import org.junit.Test;

public class PlayerManagerTest {

    @Test
    public void should_create_clear_boxes_and_return_them(){
        PlayerManager playerManager = new PlayerManager(6);
        Assert.assertEquals(6, playerManager.getBoxes().length);
        for (int i = 0; i < playerManager.getBoxes().length; i++) {
            Assert.assertNotNull(playerManager.getBoxes()[i]);
        }
    }

    @Test
    public void should_return_active_boxes(){
        PlayerManager playerManager = new PlayerManager(6);
        playerManager.getBoxes()[0].activate(new Player());
        playerManager.getBoxes()[2].activate(new Player());
        playerManager.getBoxes()[5].activate(new Player());

        Sit playingBoxes[] = playerManager.getPlayingBoxes();

        Assert.assertEquals(3, playingBoxes.length);
        Assert.assertEquals(playerManager.getBoxes()[0], playingBoxes[0]);
        Assert.assertEquals(playerManager.getBoxes()[2], playingBoxes[1]);
        Assert.assertEquals(playerManager.getBoxes()[5], playingBoxes[2]);
    }
}
