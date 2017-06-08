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
        Assert.assertEquals(6, table.getBoxCount());
        for (int i = 0; i < table.getBoxCount(); i++) {
            Assert.assertNotNull(table.getBox(i));
        }
    }

    @Test
    public void should_init_game_with_activated_table_boxes(){
        Table table = createTable();
        table.getBox(0).isActivated(true);
        table.getBox(2).isActivated(true);
        table.getBox(4).isActivated(true);

        IRound round = table.startRound();

        Assert.assertEquals(3, round.getPlayerCount());
    }


}
