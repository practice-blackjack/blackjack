package nulp.pist21.blackjack.model.game.round;

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
        boxes[1].setBet(100);
        boxes[2].setBet(100);

        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        BetRound round = new BetRound(boxes);
        round.end();

        for(TableBox box: boxes){
            Assert.assertEquals(0, box.getBet());
        }
    }
}
