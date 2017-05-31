package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.table.deck.EndlessDeck;
import nulp.pist21.blackjack.model.table.game.GameWithDealer;
import org.junit.Assert;
import org.junit.Test;

public class TableTest {

    public Table createTable(){
        GameWithDealer game = new GameWithDealer(new EndlessDeck(), new DealerBox());
        return new Table(6, game);
    }

    @Test
    public void should_create_clear_boxes(){
        Table table = createTable();
        Assert.assertNotNull (table.getBoxes());
        Assert.assertEquals(6, table.getBoxes().length);
        for (TableBox box: table.getBoxes()) {
            Assert.assertNotNull(box);
        }
    }


}
