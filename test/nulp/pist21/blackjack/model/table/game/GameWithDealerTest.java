package nulp.pist21.blackjack.model.table.game;

import nulp.pist21.blackjack.model.table.IBox;
import nulp.pist21.blackjack.model.table.DealerBox;
import nulp.pist21.blackjack.model.table.TableBox;
import nulp.pist21.blackjack.model.table.deck.Card;
import nulp.pist21.blackjack.model.table.deck.EndlessDeck;
import org.junit.Assert;
import org.junit.Test;

public class GameWithDealerTest {

    @Test
    public void should_give_first_cards(){
        GameWithDealer game = new GameWithDealer(new EndlessDeck());

        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };

        game.start(boxes);

        for(IBox box: game.getPlayingBoxes()){
            Assert.assertEquals(2, box.getHand().length);
        }
    }

    @Test
    public void should_take_cards(){
        GameWithDealer game = new GameWithDealer(new EndlessDeck());
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
    }

    @Test
    public void should_return_cards_value(){
        IBox boxes[] = new TableBox[10];
        for (int i = 0; i < boxes.length; i++){
            boxes[i] = new TableBox();
        }

        GameWithDealer game = new GameWithDealer(new EndlessDeck());
        game.start(boxes);
        game.end();

        boxes[0].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[0].giveCard(new Card(Card.CLUBS, Card._7));

        boxes[1].giveCard(new Card(Card.CLUBS, Card._8));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._7));

        boxes[2].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._7));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.ACE));

        boxes[3].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[3].giveCard(new Card(Card.CLUBS, Card._7));
        boxes[3].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[3].giveCard(new Card(Card.CLUBS, Card._8));

        boxes[4].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[4].giveCard(new Card(Card.CLUBS, Card.ACE));

        boxes[5].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[5].giveCard(new Card(Card.CLUBS, Card.JACK));

        boxes[6].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[6].giveCard(new Card(Card.CLUBS, Card.QUEEN));

        boxes[7].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[7].giveCard(new Card(Card.CLUBS, Card.KING));

        boxes[8].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[8].giveCard(new Card(Card.CLUBS, Card._5));
        boxes[8].giveCard(new Card(Card.CLUBS, Card._5));


        boxes[9].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[9].giveCard(new Card(Card.CLUBS, Card.ACE));



        Assert.assertEquals(18, game.getValue(0));
        Assert.assertEquals(15, game.getValue(1));
        Assert.assertEquals(19, game.getValue(2));
        Assert.assertEquals(17, game.getValue(3));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(4));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(5));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(6));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(7));
        Assert.assertEquals(21, game.getValue(8));
        Assert.assertEquals(12, game.getValue(9));

    }

    @Test
    public void should_ignore_hidden_card_in_sum(){
        GameWithDealer game = new GameWithDealer(new EndlessDeck());
        game.start(new TableBox[]{});
        game.end();

        game.getPlayingBoxes()[0].giveCard(new Card(Card.CLUBS, Card.ACE));
        game.getPlayingBoxes()[0].giveCard(new Card(Card.HEARTS, Card._7));
        Assert.assertEquals(7, game.getValue(0));
    }
}
