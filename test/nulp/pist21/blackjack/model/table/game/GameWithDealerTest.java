package nulp.pist21.blackjack.model.table.game;

import nulp.pist21.blackjack.model.table.IBox;
import nulp.pist21.blackjack.model.table.TableBox;
import nulp.pist21.blackjack.model.table.deck.Card;
import nulp.pist21.blackjack.model.table.deck.EndlessDeck;
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

        for(IBox box: game.getPlayingBoxes()){
            Assert.assertEquals(2, box.getHand().length);
        }
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
    }

    @Test
    public void should_return_cards_value(){
        IBox boxes[] = new TableBox[6];
        for (int i = 0; i < boxes.length; i++){
            boxes[i] = new TableBox();
        }

        IGame game = new GameWithDealer(new EndlessDeck());
        game.start(boxes);
        game.end();

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


        Assert.assertEquals(18, game.getValue(0));
        Assert.assertEquals(15, game.getValue(1));
        Assert.assertEquals(19, game.getValue(2));
        Assert.assertEquals(17, game.getValue(3));
        Assert.assertEquals(21, game.getValue(4));
        Assert.assertEquals(21, game.getValue(5));
    }

    @Test
    public void should_return_black_jack(){
        IBox boxes[] = new TableBox[8];
        for (int i = 0; i < boxes.length; i++){
            boxes[i] = new TableBox();
        }

        IGame game = new GameWithDealer(new EndlessDeck());
        game.start(boxes);
        game.end();

        boxes[0].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[0].giveCard(new Card(Card.CLUBS, Card.ACE));

        boxes[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[1].giveCard(new Card(Card.CLUBS, Card.JACK));

        boxes[2].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));

        boxes[3].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[3].giveCard(new Card(Card.CLUBS, Card.KING));

        boxes[4].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[4].giveCard(new Card(Card.CLUBS, Card._10));

        boxes[5].giveCard(new Card(Card.CLUBS, Card.JACK));
        boxes[5].giveCard(new Card(Card.CLUBS, Card.ACE));

        boxes[6].giveCard(new Card(Card.CLUBS, Card.QUEEN));
        boxes[6].giveCard(new Card(Card.CLUBS, Card.ACE));

        boxes[7].giveCard(new Card(Card.CLUBS, Card.KING));
        boxes[7].giveCard(new Card(Card.CLUBS, Card.ACE));


        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(0));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(1));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(2));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(3));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(4));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(5));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(6));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, game.getValue(7));
    }

    @Test
    public void should_return_a_lot(){
        IBox boxes[] = new TableBox[2];
        for (int i = 0; i < boxes.length; i++){
            boxes[i] = new TableBox();
        }

        IGame game = new GameWithDealer(new EndlessDeck());
        game.start(boxes);
        game.end();

        boxes[0].giveCard(new Card(Card.CLUBS, Card.KING));
        boxes[0].giveCard(new Card(Card.CLUBS, Card._7));
        boxes[0].giveCard(new Card(Card.CLUBS, Card.KING));     //a lot(27)

        boxes[1].giveCard(new Card(Card.CLUBS, Card._9));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._8));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._5));       // a lot(22)

        boxes[1].giveCard(new Card(Card.CLUBS, Card.KING));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._8));
        boxes[1].giveCard(new Card(Card.CLUBS, Card.QUEEN));       // a lot(28)

        Assert.assertEquals(GameWithDealer.A_LOT, game.getValue(0));
        Assert.assertEquals(GameWithDealer.A_LOT, game.getValue(1));
    }

    @Test
    public void should_ignore_hidden_card_in_sum(){
        IGame game = new GameWithDealer(new EndlessDeck());
        game.start(new TableBox[]{});
        game.end();

        game.getPlayingBoxes()[0].giveCard(new Card(Card.CLUBS, Card.ACE));
        game.getPlayingBoxes()[0].giveCard(new Card(Card.HEARTS, Card._7));
        Assert.assertEquals(7, game.getValue(0));
    }

    @Test
    public void should_return_winners_koefs(){

    }
}
