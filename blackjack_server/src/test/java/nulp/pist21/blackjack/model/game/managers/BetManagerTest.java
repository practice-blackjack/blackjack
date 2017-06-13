package nulp.pist21.blackjack.model.game.managers;

import nulp.pist21.blackjack.model.table.TableBox;
import org.junit.Assert;
import org.junit.Test;

public class BetManagerTest {
    @Test
    public void should_take_bets(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        BetManager bets = new BetManager(100, 300);
        bets.start(boxes);
        int i = 0;

        while(!bets.isOver()){
            bets.next((i + 1) * 100);
            i++;
        }

        Assert.assertEquals(3, i);

        Assert.assertEquals(100, boxes[0].getBet());
        Assert.assertEquals(200, boxes[1].getBet());
        Assert.assertEquals(300, boxes[2].getBet());
    }

    @Test
    public void should_clear_bets(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        BetManager bets = new BetManager(100, 300);
        bets.start(boxes);
        int i = 0;

        while(!bets.isOver()){
            bets.next((i + 1) * 100);
            i++;
        }
        bets.end();

        Assert.assertEquals(0, boxes[0].getBet());
        Assert.assertEquals(0, boxes[1].getBet());
        Assert.assertEquals(0, boxes[2].getBet());
    }

    @Test
    public void should_return_false_if_bet_is_not_in_range(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox(),
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        BetManager bets = new BetManager(100, 300);
        bets.start(boxes);

        Assert.assertTrue(bets.next(100));
        Assert.assertFalse(bets.next(99));
        Assert.assertFalse(bets.next(0));
        Assert.assertFalse(bets.next(-100));
        Assert.assertFalse(bets.next(301));
        Assert.assertTrue(bets.next(300));
    }
}
