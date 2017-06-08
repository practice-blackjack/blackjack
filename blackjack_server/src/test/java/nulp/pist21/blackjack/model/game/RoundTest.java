package nulp.pist21.blackjack.model.game;

import mock.UserMock;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.table.TableBox;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import org.junit.Assert;
import org.junit.Test;

public class RoundTest {

    @Test
    public void should_give_first_cards(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        IRound round = new Round(boxes, new EndlessDeck());

        round.start();

        for(int i = 0; i < round.getPlayerCount(); i++){
            Assert.assertEquals(2, round.getPlayer(i).getHand().length);
        }
        Assert.assertEquals(2, round.getPlayer(Round.DEALER_INDEX).getHand().length);
}

    @Test
    public void should_take_cards(){
        TableBox[] boxes = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        IRound game = new Round(boxes, new EndlessDeck());

        game.start();
        game.end();
        for(TableBox box: boxes){
            Assert.assertEquals(0, box.getHand().length);
        }
        Assert.assertEquals(0, game.getPlayer(Round.DEALER_INDEX).getHand().length);
    }

    @Test
    public void should_ignore_action_if_a_lot(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        IDeck deck = new EndlessDeck();


        IRound round = new Round(boxes, deck);
        round.start();

        for(TableBox box: boxes){
            box.takeCards();
        }

        boxes[0].giveCard(new Card(Card.CLUBS, Card.JACK));
        boxes[0].giveCard(new Card(Card.CLUBS, Card._7));
        boxes[0].giveCard(new Card(Card.CLUBS, Card._8));       //25

        boxes[1].giveCard(new Card(Card.CLUBS, Card.JACK));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._8));       //28

        boxes[2].giveCard(new Card(Card.CLUBS, Card.JACK));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._2));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));    //22

        Assert.assertFalse(round.next(new GameAction(GameAction.Actions.HIT), deck));
    }

    @Test
    public void should_ignore_action_if_21(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        IDeck deck = new EndlessDeck();


        IRound round = new Round(boxes, deck);
        round.start();

        for(TableBox box: boxes){
            box.takeCards();
        }

        boxes[0].giveCard(new Card(Card.CLUBS, Card.JACK));
        boxes[0].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[0].giveCard(new Card(Card.CLUBS, Card.KING));

        boxes[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));

        boxes[2].giveCard(new Card(Card.CLUBS, Card._7));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._5));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._9));

        Assert.assertFalse(round.next(new GameAction(GameAction.Actions.HIT), deck));
    }

    @Test
    public void should_ignore_action_if_blackjack(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        IDeck deck = new EndlessDeck();


        IRound round = new Round(boxes, deck);
        round.start();

        for(TableBox box: boxes){
            box.takeCards();
        }

        boxes[0].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[0].giveCard(new Card(Card.CLUBS, Card.KING));

        boxes[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));

        boxes[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.ACE));

        Assert.assertFalse(round.next(new GameAction(GameAction.Actions.HIT), deck));
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

        IRound round = new Round(boxes, new EndlessDeck());
        round.start();

        GameAction action;
        do{
            int userId = round.getCurrentIndex();
            action = users[userId].doStep(round, userId);
        } while (round.next(action, new EndlessDeck()));
    }

}
