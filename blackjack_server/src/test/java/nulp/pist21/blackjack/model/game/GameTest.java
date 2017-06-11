package nulp.pist21.blackjack.model.game;


import mock.UserMock;
import nulp.pist21.blackjack.model.actions.Action;
import nulp.pist21.blackjack.model.actions.BetAction;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.game.round.IRound;
import nulp.pist21.blackjack.model.table.Table;
import nulp.pist21.blackjack.model.table.TableBox;
import org.junit.Assert;
import org.junit.Test;

public class GameTest {
    @Test
    public void should_init_game_with_activated_table_boxes(){
        Table table = new Table(6, 100, 1000, new EndlessDeck());
        table.getBoxes()[0].isActivated(true);
        table.getBoxes()[2].isActivated(true);
        table.getBoxes()[4].isActivated(true);

        IGame game = new GameWithDealer(table);
        game.start();
        int i = 0;

        IRound betRound = game.getCurrentRound();
        while(!betRound.isEnd()){
            Assert.assertTrue(game.getCurrentRound().next(new BetAction((i + 1) * 100)));
            i++;
        }
        Assert.assertEquals(3, i);

        Assert.assertEquals(100, table.getBoxes()[0].getBet());
        Assert.assertEquals(0, table.getBoxes()[1].getBet());
        Assert.assertEquals(200, table.getBoxes()[2].getBet());
        Assert.assertEquals(0, table.getBoxes()[3].getBet());
        Assert.assertEquals(300, table.getBoxes()[4].getBet());
        Assert.assertEquals(0, table.getBoxes()[5].getBet());
    }

    @Test
    public void should_clean_after_previous_game_on_start_of_new(){
        Table table = new Table(6, 100, 1000, new EndlessDeck());
        table.getBoxes()[0].isActivated(true);
        table.getBoxes()[2].isActivated(true);
        table.getBoxes()[4].isActivated(true);

        UserMock users[] = new UserMock[]{
                new UserMock(12, 100),
                new UserMock(15, 200),
                new UserMock(12, 200),
                new UserMock(18, 300),
                new UserMock(18, 300),
                new UserMock(18, 300),
                new UserMock(18, 300),
        };

        IGame game = new GameWithDealer(table);

        game.start();

        while(!game.isOver()){
            Action action = users[game.getCurrentIndex()].doStep(game);
            game.getCurrentRound().next(action);
        }

        game.start();

        for (TableBox box: table.getBoxes()){
            Assert.assertEquals(0, box.getBet());
            Assert.assertEquals(0, box.getHand().length);
        }
    }

    @Test
    public void should_return_current_index(){
        Table table = new Table(6, 100, 1000, new EndlessDeck());
        table.getBoxes()[0].isActivated(true);
        table.getBoxes()[2].isActivated(true);
        table.getBoxes()[4].isActivated(true);

        UserMock users[] = new UserMock[]{
                new UserMock(12, 100),
                new UserMock(15, 200),
                new UserMock(12, 200),
                new UserMock(18, 300),
                new UserMock(18, 300),
                new UserMock(18, 300),
                new UserMock(18, 300),
        };

        IGame game = new GameWithDealer(table);

        game.start();

        for (int i = 0; i < 3; i++){
            Assert.assertEquals(i * 2, game.getCurrentIndex());
            Action action = new BetAction((i + 1) * 100);
            game.getCurrentRound().next(action);
        }
    }

    @Test
    public void should_return_current_box(){
        Table table = new Table(6, 100, 1000, new EndlessDeck());
        table.getBoxes()[0].isActivated(true);
        table.getBoxes()[2].isActivated(true);
        table.getBoxes()[4].isActivated(true);

        UserMock users[] = new UserMock[]{
                new UserMock(12, 100),
                new UserMock(15, 200),
                new UserMock(12, 200),
                new UserMock(18, 300),
                new UserMock(18, 300),
                new UserMock(18, 300),
                new UserMock(18, 300),
        };

        IGame game = new GameWithDealer(table);
        game.start();

        for (int i = 0; i < 3; i++){
            Assert.assertEquals(table.getBoxes()[i * 2], game.getCurrentBox());
            Action action = new BetAction((i + 1) * 100);
            game.getCurrentRound().next(action);
        }
    }

    @Test
    public void should_tell_when_game_is_over(){
        Table table = new Table(6, 100, 1000, new EndlessDeck());
        table.getBoxes()[0].isActivated(true);
        table.getBoxes()[2].isActivated(true);
        table.getBoxes()[4].isActivated(true);

        UserMock users[] = new UserMock[]{
                new UserMock(12, 100),
                new UserMock(15, 200),
                new UserMock(12, 200),
                new UserMock(18, 300),
                new UserMock(18, 300),
                new UserMock(18, 300),
                new UserMock(18, 300),
        };

        IGame game = new GameWithDealer(table);

        game.start();

        for (int i = 0; i < 3; i++){
            Assert.assertEquals(table.getBoxes()[i * 2], game.getCurrentBox());
            Action action = new BetAction((i + 1) * 100);
            game.getCurrentRound().next(action);
        }
    }
}
