package nulp.pist21.blackjack.model.game.managers;

import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.game.Dealer;
import nulp.pist21.blackjack.model.table.TableBox;
import org.junit.Assert;
import org.junit.Test;
import nulp.pist21.blackjack.model.game.managers.PlayManager.Actions;

public class PlayManagerTest {
    @Test
    public void should_give_first_cards(){
        TableBox boxes[] = new TableBox[]{
                new TableBox(),
                new TableBox(),
                new TableBox()
        };
        IDeck deck = new EndlessDeck();
        Dealer dealer = new Dealer();

        PlayManager play = new PlayManager(deck, dealer);
        play.start(boxes);

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

        PlayManager play = new PlayManager(deck, dealer);
        play.start(boxes);
        play.end();
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

        PlayManager play = new PlayManager(deck, dealer);
        play.start(boxes);

        play.end();

        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));

        boxes[2].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._3));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));

        play.next(Actions.STAND);

        Assert.assertTrue(play.isOver());
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

        PlayManager play = new PlayManager(deck, dealer);
        play.start(boxes);

        play.end();

        boxes[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));

        boxes[2].giveCard(new Card(Card.CLUBS, Card._7));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._5));
        boxes[2].giveCard(new Card(Card.CLUBS, Card._9));

        play.next(Actions.STAND);

        Assert.assertTrue(play.isOver());
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


        PlayManager play = new PlayManager(deck, dealer);
        play.start(boxes);

        play.end();

        boxes[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        boxes[1].giveCard(new Card(Card.CLUBS, Card._10));

        boxes[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));
        boxes[2].giveCard(new Card(Card.CLUBS, Card.ACE));

        play.next(Actions.STAND);

        Assert.assertTrue(play.isOver());
    }
}
