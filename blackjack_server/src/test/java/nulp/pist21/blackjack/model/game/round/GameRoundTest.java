package nulp.pist21.blackjack.model.game.round;

import mock.UserMock;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.game.Dealer;
import nulp.pist21.blackjack.model.table.TableBox;
import org.junit.Assert;
import org.junit.Test;

public class GameRoundTest {

    @Test
    public void should_give_first_cards(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };
        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        GameRound round = new GameRound(boxes, deck, dealer);

        for(TableBox box: boxes){
            Assert.assertEquals(2, box.getHand().length);
        }
        Assert.assertEquals(2, dealer.getHand().length);
    }

    @Test
    public void should_take_cards(){
        TableBox[] boxes = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };
        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        IRound round = new GameRound(boxes, deck, dealer);
        round.end();
        for(TableBox box: boxes){
            Assert.assertEquals(0, box.getHand().length);
        }
        Assert.assertEquals(0, dealer.getHand().length);
    }

    @Test
    public void should_go_to_next_if_a_lot(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };
        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        IRound round = new GameRound(boxes, deck, dealer);

        round.end();

        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));

        boxes[2].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._3));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));

        round.next(new GameAction(GameAction.Actions.STAND));

        Assert.assertTrue(round.isEnd());
    }

    @Test
    public void should_go_to_next_if_21(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };
        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        IRound round = new GameRound(boxes, deck, dealer);

        round.end();

        boxes[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));

        boxes[2].giveCard(new Card(Card.CLUBS, Card._7));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._5));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._9));

        round.next(new GameAction(GameAction.Actions.STAND));

        Assert.assertFalse(round.isEnd());
    }

    @Test
    public void should_go_to_next_if_blackjack(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };
        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();


        IRound round = new GameRound(boxes, deck, dealer);

        round.end();

        boxes[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));

        boxes[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.ACE));

        round.next(new GameAction(GameAction.Actions.STAND));

        Assert.assertFalse(round.isEnd());
    }

    @Test
    public void should_work_game_circle(){

        UserMock users[] = new UserMock[]{
                new UserMock(14),
                new UserMock(16),
                new UserMock(18),
        };

        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };
        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        IRound round = new GameRound(boxes, deck, dealer);

        do{
            Assert.assertTrue(round.next(new GameAction(GameAction.Actions.HIT)));
        } while (!round.isEnd());
    }

}
