package nulp.pist21.blackjack.model.table.game;

import mock.UserMock;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.TableBox;
import nulp.pist21.blackjack.model.table.deck.Card;
import nulp.pist21.blackjack.model.table.deck.EndlessDeck;
import nulp.pist21.blackjack.model.table.deck.TurnableCard;
import org.junit.Assert;
import org.junit.Test;

public class GameWithDealerTest {

    @Test
    public void should_give_first_cards(){
        IGame game = new GameWithDealer(new EndlessDeck());

        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        game.start(boxes);

        for(int i = 0; i < game.getBoxCount(); i++){
            Assert.assertEquals(2, game.getBox(i).getHand().length);
        }
        Assert.assertEquals(2, game.getBox(GameWithDealer.DEALER_INDEX).getHand().length);
    }

    @Test
    public void should_take_cards(){
        IGame game = new GameWithDealer(new EndlessDeck());
        TableBox[] boxes = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        game.start(boxes);
        game.end();
        for(TableBox box: boxes){
            Assert.assertEquals(0, box.getHand().length);
        }
        Assert.assertEquals(0, game.getBox(GameWithDealer.DEALER_INDEX).getHand().length);
    }

    @Test
    public void should_return_cards_value(){
        TableBox boxes[] = new TableBox[6];
        for (int i = 0; i < boxes.length; i++){
            boxes[i] = new TableBox();
        }

        boxes[0].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[0].giveCard(new Card(Card.CLUBS, Card._7));       //18

        boxes[1].giveCard(new Card(Card.CLUBS, Card._8));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._7));       //15

        boxes[2].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._7));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.ACE));      //19

        boxes[3].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[3].giveCard(new Card(Card.CLUBS, Card._7));
        boxes[3].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[3].giveCard(new Card(Card.CLUBS, Card._8));       //17

        boxes[4].giveCard(new Card(Card.CLUBS, Card.KING));
        boxes[4].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[4].giveCard(new Card(Card.CLUBS, Card.KING));     //21

        boxes[5].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[5].giveCard(new Card(Card.CLUBS, Card._5));
        boxes[5].giveCard(new Card(Card.CLUBS, Card._5));       //21


        Assert.assertEquals(18, GameWithDealer.Combination.getPoints(boxes[0].getHand()));
        Assert.assertEquals(15, GameWithDealer.Combination.getPoints(boxes[1].getHand()));
        Assert.assertEquals(19, GameWithDealer.Combination.getPoints(boxes[2].getHand()));
        Assert.assertEquals(17, GameWithDealer.Combination.getPoints(boxes[3].getHand()));
        Assert.assertEquals(21, GameWithDealer.Combination.getPoints(boxes[4].getHand()));
        Assert.assertEquals(21, GameWithDealer.Combination.getPoints(boxes[5].getHand()));
    }

    @Test
    public void should_return_black_jack(){
        TableBox boxes[] = new TableBox[8];
        for (int i = 0; i < boxes.length; i++){
            boxes[i] = new TableBox();
        }

        IGame game = new GameWithDealer(new EndlessDeck());
        game.start(boxes);
        game.end();

        boxes[0].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[0].giveCard(new Card(Card.CLUBS, Card._10));

        boxes[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[1].giveCard(new Card(Card.CLUBS, Card.JACK));

        boxes[2].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));

        boxes[3].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[3].giveCard(new Card(Card.CLUBS, Card.KING));

        boxes[4].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[4].giveCard(new Card(Card.CLUBS, Card.ACE));

        boxes[5].giveCard(new Card(Card.CLUBS, Card.JACK));
        boxes[5].giveCard(new Card(Card.CLUBS, Card.ACE));

        boxes[6].giveCard(new Card(Card.CLUBS, Card.QUEEN));
        boxes[6].giveCard(new Card(Card.CLUBS, Card.ACE));

        boxes[7].giveCard(new Card(Card.CLUBS, Card.KING));
        boxes[7].giveCard(new Card(Card.CLUBS, Card.ACE));


        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[0].getHand()));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[1].getHand()));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[2].getHand()));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[3].getHand()));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[4].getHand()));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[5].getHand()));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[6].getHand()));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[7].getHand()));
    }

    @Test
    public void should_return_a_lot(){
        TableBox boxes[] = new TableBox[4];
        for (int i = 0; i < boxes.length; i++){
            boxes[i] = new TableBox();
        }

        boxes[0].giveCard(new Card(Card.CLUBS, Card.KING));
        boxes[0].giveCard(new Card(Card.CLUBS, Card._7));
        boxes[0].giveCard(new Card(Card.CLUBS, Card.KING));     //a lot(27)

        boxes[1].giveCard(new Card(Card.CLUBS, Card._9));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._8));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._5));       // a lot(22)

        boxes[2].giveCard(new Card(Card.CLUBS, Card.KING));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._8));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));       // a lot(28)

        boxes[3].giveCard(new Card(Card.CLUBS, Card.KING));
        boxes[3].giveCard(new Card(Card.CLUBS, Card._8));
        boxes[3].giveCard(new Card(Card.CLUBS, Card._2));
        boxes[3].giveCard(new Card(Card.CLUBS, Card.QUEEN));       // a lot(30)

        Assert.assertTrue(GameWithDealer.Combination.IsALot(boxes[0].getHand()));
        Assert.assertTrue(GameWithDealer.Combination.IsALot(boxes[1].getHand()));
        Assert.assertTrue(GameWithDealer.Combination.IsALot(boxes[2].getHand()));
        Assert.assertTrue(GameWithDealer.Combination.IsALot(boxes[3].getHand()));
    }

    @Test
    public void should_give_hidden_card_for_dealer(){
        GameWithDealer game = new GameWithDealer(new EndlessDeck());
        game.start(new TableBox[]{});
        Assert.assertEquals(Card.UNDEFINED_SUIT, game.getBox(GameWithDealer.DEALER_INDEX).getHand()[0].getSuit());
        Assert.assertEquals(Card.UNDEFINED_VALUE, game.getBox(GameWithDealer.DEALER_INDEX).getHand()[0].getValue());
    }

    @Test
    public void should_ignore_hidden_card_in_sum(){
        TableBox[] boxes = new TableBox[]{
                new TableBox(),
        };

        boxes[0].giveCard(new TurnableCard(new Card(Card.CLUBS, Card.ACE)));
        boxes[0].giveCard(new Card(Card.HEARTS, Card._9));


        Assert.assertEquals(9, GameWithDealer.Combination.getPoints(boxes[0].getHand()));
    }

    @Test
    public void should_open_hidden_card_on_dealers_step(){
        GameWithDealer game = new GameWithDealer(new EndlessDeck());
        game.start(new TableBox[]{});
        TableBox dealerBox = game.getBox(GameWithDealer.DEALER_INDEX);
        game.next(new GameAction(GameAction.Actions.STAND));
        Assert.assertNotEquals(Card.UNDEFINED_SUIT, dealerBox.getHand()[0].getSuit());
        Assert.assertNotEquals(Card.UNDEFINED_VALUE, dealerBox.getHand()[0].getValue());
    }

    @Test
    public void should_work_game_circle(){
        IGame game = new GameWithDealer(new EndlessDeck());
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

        game.start(boxes);

        GameAction action;
        do{
            int userId = game.getCurrentIndex();
            action = users[userId].doStep(game, userId);
        } while (game.next(action));
    }

    @Test
    public void should_return_winners_koefs(){

    }
}
