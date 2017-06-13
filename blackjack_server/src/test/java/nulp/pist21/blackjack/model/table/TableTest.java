package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.Player;
import org.junit.Assert;
import org.junit.Test;

public class TableTest {

    @Test
    public void should_create_clear_boxes_and_return_them(){
        Table table = new Table(6);
        Assert.assertEquals(6, table.getBoxes().length);
        for (int i = 0; i < table.getBoxes().length; i++) {
            Assert.assertNotNull(table.getBoxes()[i]);
        }
    }

    @Test
    public void should_return_active_boxes(){
        Table table = new Table(6);
        table.getBoxes()[0].activate(new Player());
        table.getBoxes()[2].activate(new Player());
        table.getBoxes()[5].activate(new Player());

        TableBox playingBoxes[] = table.getPlayingBoxes();

        Assert.assertEquals(3, playingBoxes.length);
        Assert.assertEquals(table.getBoxes()[0], playingBoxes[0]);
        Assert.assertEquals(table.getBoxes()[2], playingBoxes[1]);
        Assert.assertEquals(table.getBoxes()[5], playingBoxes[2]);
    }
}
