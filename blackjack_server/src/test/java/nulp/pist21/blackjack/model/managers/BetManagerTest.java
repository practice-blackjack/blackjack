package nulp.pist21.blackjack.model.managers;

import org.junit.Assert;
import org.junit.Test;

public class BetManagerTest {
    @Test
    public void should_take_bets(){
        BetManager bets = new BetManager(100, 300);
        bets.start(3);
        int i = 0;

        while(!bets.isOver()){
            bets.next((i + 1) * 100);
            i++;
        }

        Assert.assertEquals(3, i);

        Assert.assertEquals(100, bets.getBanks()[0]);
        Assert.assertEquals(200, bets.getBanks()[1]);
        Assert.assertEquals(300, bets.getBanks()[2]);
    }

    @Test
    public void should_return_false_if_bet_is_not_in_range(){
        BetManager bets = new BetManager(100, 300);
        bets.start(6);

        Assert.assertTrue(bets.next(100));
        Assert.assertFalse(bets.next(99));
        Assert.assertFalse(bets.next(0));
        Assert.assertFalse(bets.next(-100));
        Assert.assertFalse(bets.next(301));
        Assert.assertTrue(bets.next(300));
    }

    @Test
    public void should_take_money_from_player(){

    }

    @Test
    public void should_giveMoney_to_player(){

    }
}
