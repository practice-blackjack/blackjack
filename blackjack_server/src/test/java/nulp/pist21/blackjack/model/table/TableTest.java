package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.deck.EndlessDeck;
import org.junit.Assert;
import org.junit.Test;

public class TableTest {

    @Test
    public void should_create_clear_boxes_and_return_them(){
        Table table = new Table(6, 100, 1000, new EndlessDeck());
        Assert.assertEquals(6, table.getBoxes().length);
        for (int i = 0; i < table.getBoxes().length; i++) {
            Assert.assertNotNull(table.getBoxes()[i]);
        }
    }
}
