package nulp.pist21.blackjack.model.table;

import mock.UserMock;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.deck.Card;
import nulp.pist21.blackjack.model.table.deck.EndlessDeck;
import nulp.pist21.blackjack.model.table.deck.IDeck;
import nulp.pist21.blackjack.model.table.game.GameWithDealer;
import org.junit.Assert;
import org.junit.Test;

public class TableTest {

    public Table createTable(){
        IDeck deck = new EndlessDeck();
        GameWithDealer game = new GameWithDealer(deck);
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

    @Test
    public void should_work_game_circle(){
        Table table = new Table(3, new GameWithDealer(new EndlessDeck()));
        UserMock users[] = new UserMock[]{
                new UserMock(14),
                new UserMock(16),
                new UserMock(18),
        };

        table.getGame().start(table.getBoxes());

        GameAction action;
        do{
            int userId = table.getGame().getCurrentIndex();
            action = users[userId].doStep(table.getGame(), userId);
        } while (table.getGame().next(action));
        int i = 0;
    }
}
