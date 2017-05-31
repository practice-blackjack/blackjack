package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.table.deck.EndlessDeck;
import nulp.pist21.blackjack.model.table.game.GameWithDealer;
import org.junit.Assert;
import org.junit.Test;

public class TableTest {

    public Table createTable(){
        GameWithDealer game = new GameWithDealer(new EndlessDeck());
        return new Table(6, game);
    }

    @Test
    public void should_create_clear_boxes(){
        Table table = createTable();
        Assert.assertEquals(6, table.getBoxCount());
        for (int i = 0; i < table.getBoxCount(); i++) {
            Assert.assertNotNull(table.getBox(i));
        }
    }


}
