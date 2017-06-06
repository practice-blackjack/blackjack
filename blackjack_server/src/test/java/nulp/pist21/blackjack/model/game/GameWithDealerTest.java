package nulp.pist21.blackjack.model.game;

import mock.DeckMock;
import mock.UserMock;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.table.TableBox;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.deck.TurnableCard;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameWithDealerTest {

    @Test
    public void should_give_first_cards(){
        IGame game = new GameWithDealer();

        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        game.start(boxes, new EndlessDeck());

        for(int i = 0; i < game.getPlayerCount(); i++){
            Assert.assertEquals(2, game.getPlayer(i).getHand().length);
        }
        Assert.assertEquals(2, game.getPlayer(GameWithDealer.DEALER_INDEX).getHand().length);
    }

    @Test
    public void should_take_cards(){
        IGame game = new GameWithDealer();
        TableBox[] boxes = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        game.start(boxes, new EndlessDeck());
        game.end();
        for(TableBox box: boxes){
            Assert.assertEquals(0, box.getHand().length);
        }
        Assert.assertEquals(0, game.getPlayer(GameWithDealer.DEALER_INDEX).getHand().length);
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


        Assert.assertEquals(18, GameWithDealer.Combination.getPoints(boxes[0]));
        Assert.assertEquals(15, GameWithDealer.Combination.getPoints(boxes[1]));
        Assert.assertEquals(19, GameWithDealer.Combination.getPoints(boxes[2]));
        Assert.assertEquals(17, GameWithDealer.Combination.getPoints(boxes[3]));
        Assert.assertEquals(21, GameWithDealer.Combination.getPoints(boxes[4]));
        Assert.assertEquals(21, GameWithDealer.Combination.getPoints(boxes[5]));
    }

    @Test
    public void should_return_black_jack(){
        TableBox boxes[] = new TableBox[8];
        for (int i = 0; i < boxes.length; i++){
            boxes[i] = new TableBox();
        }

        IGame game = new GameWithDealer();
        game.start(boxes, new EndlessDeck());
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


        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[0]));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[1]));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[2]));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[3]));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[4]));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[5]));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[6]));
        Assert.assertEquals(GameWithDealer.Combination.BLACK_JACK, GameWithDealer.Combination.getPoints(boxes[7]));
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

        Assert.assertTrue(GameWithDealer.Combination.isALot(boxes[0]));
        Assert.assertTrue(GameWithDealer.Combination.isALot(boxes[1]));
        Assert.assertTrue(GameWithDealer.Combination.isALot(boxes[2]));
        Assert.assertTrue(GameWithDealer.Combination.isALot(boxes[3]));
    }

    @Test
    public void should_ignore_hidden_card_in_sum(){
        TableBox[] boxes = new TableBox[]{
                new TableBox(),
        };

        boxes[0].giveCard(new TurnableCard(new Card(Card.CLUBS, Card.ACE)));
        boxes[0].giveCard(new Card(Card.HEARTS, Card._9));


        Assert.assertEquals(9, GameWithDealer.Combination.getPoints(boxes[0]));
    }

    @Test
    public void should_return_winners_koefs(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        boxes[0].giveCard(new Card(Card.CLUBS, Card.KING));
        boxes[0].giveCard(new Card(Card.CLUBS, Card._8));
        boxes[0].giveCard(new Card(Card.CLUBS, Card.QUEEN));    // a lot(28)

        boxes[1].giveCard(new Card(Card.CLUBS, Card.KING));
        boxes[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[1].giveCard(new Card(Card.CLUBS, Card.KING));     //21

        boxes[2].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.JACK));     //Black Jack

        boxes[3].giveCard(new Card(Card.CLUBS, Card._8));
        boxes[3].giveCard(new Card(Card.CLUBS, Card._7));       //15

        boxes[4].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[4].giveCard(new Card(Card.CLUBS, Card._7));
        boxes[4].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[4].giveCard(new Card(Card.CLUBS, Card._8));       //17


        Assert.assertEquals(0, GameWithDealer.Combination.getWin(
                boxes[0], boxes[1]), 0);  //28 - 21
        Assert.assertEquals(2, GameWithDealer.Combination.getWin(
                boxes[1], boxes[0]), 0);  //21 - 28


        Assert.assertEquals(2, GameWithDealer.Combination.getWin(
                boxes[1], boxes[3]), 0);  //21 - 15
        Assert.assertEquals(0, GameWithDealer.Combination.getWin(
                boxes[3], boxes[1]), 0);  //15 - 21


        Assert.assertEquals(0, GameWithDealer.Combination.getWin(
                boxes[1], boxes[2]), 0);  //21 - BJ
        Assert.assertEquals(2.2, GameWithDealer.Combination.getWin(
                boxes[2], boxes[1]), 0);  //BJ - 21


        Assert.assertEquals(0, GameWithDealer.Combination.getWin(
                boxes[0], boxes[2]), 0);  //28 - BJ
        Assert.assertEquals(2.2, GameWithDealer.Combination.getWin(
                boxes[2], boxes[0]), 0);  //BJ - 28


        Assert.assertEquals(0, GameWithDealer.Combination.getWin(
                boxes[3], boxes[4]), 0);  //15 - 17
        Assert.assertEquals(2, GameWithDealer.Combination.getWin(
                boxes[4], boxes[3]), 0);  //17 - 15


        Assert.assertEquals(1, GameWithDealer.Combination.getWin(
                boxes[2], boxes[2]), 0);  //BJ - BJ
        Assert.assertEquals(1, GameWithDealer.Combination.getWin(
                boxes[4], boxes[4]), 0);  //17 - 17
        Assert.assertEquals(1, GameWithDealer.Combination.getWin(
                boxes[1], boxes[1]), 0);  //21 - 21
        Assert.assertEquals(0, GameWithDealer.Combination.getWin(
                boxes[0], boxes[0]), 0);  //28 - 28
    }

    @Test
    public void should_ignore_action_if_a_lot(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        IDeck deck = new EndlessDeck();


        IGame game = new GameWithDealer();
        game.start(boxes, deck);

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

        Assert.assertFalse(game.next(new GameAction(GameAction.Actions.HIT), deck));
    }

    @Test
    public void should_ignore_action_if_21(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        IDeck deck = new EndlessDeck();


        IGame game = new GameWithDealer();
        game.start(boxes, deck);

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

        Assert.assertFalse(game.next(new GameAction(GameAction.Actions.HIT), deck));
    }

    @Test
    public void should_ignore_action_if_blackjack(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        IDeck deck = new EndlessDeck();


        IGame game = new GameWithDealer();
        game.start(boxes, deck);

        for(TableBox box: boxes){
            box.takeCards();
        }

        boxes[0].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[0].giveCard(new Card(Card.CLUBS, Card.KING));

        boxes[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));

        boxes[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.ACE));

        Assert.assertFalse(game.next(new GameAction(GameAction.Actions.HIT), deck));
    }

    @Test
    public void should_work_game_circle(){
        IGame game = new GameWithDealer();
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

        game.start(boxes, new EndlessDeck());

        GameAction action;
        do{
            int userId = game.getCurrentIndex();
            action = users[userId].doStep(game, userId);
        } while (game.next(action, new EndlessDeck()));
    }

}
