package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.game.IRound;
import org.junit.Assert;
import org.junit.Test;

public class TableTest {

    public Table createTable(){
        return new Table(6, new EndlessDeck());
    }

    @Test
    public void should_create_clear_boxes_and_return_them(){
        Table table = createTable();
        Assert.assertEquals(6, table.getBoxes().length);
        for (int i = 0; i < table.getBoxes().length; i++) {
            Assert.assertNotNull(table.getBoxes()[i]);
        }
    }

    @Test
    public void should_init_game_with_activated_table_boxes(){
        Table table = createTable();
        table.getBoxes()[0].isActivated(true);
        table.getBoxes()[2].isActivated(true);
        table.getBoxes()[4].isActivated(true);

        IRound round = table.getRound();

        Assert.assertEquals(3, round.getHandCount());
    }


}
