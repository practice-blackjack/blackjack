package nulp.pist21.blackjack.model.game.round;

import nulp.pist21.blackjack.model.actions.BetAction;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.game.Dealer;
import nulp.pist21.blackjack.model.table.TableBox;
import org.junit.Assert;
import org.junit.Test;

public class BetRoundTest {
    @Test
    public void should_clear_bets(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        boxes[0].setBet(100);
        boxes[1].setBet(200);
        boxes[2].setBet(300);

        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        BetRound round = new BetRound(boxes, 100, 300);
        round.end();

        for(TableBox box: boxes){
            Assert.assertEquals(0, box.getBet());
        }
    }

    @Test
    public void should_return_false_if_bet_is_not_in_range(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        BetRound round = new BetRound(boxes, 100, 200);
        Assert.assertTrue(round.next(new BetAction(100)));
        Assert.assertTrue(round.next(new BetAction(150)));
        Assert.assertTrue(round.next(new BetAction(200)));
        Assert.assertFalse(round.next(new BetAction(300)));
    }
}
