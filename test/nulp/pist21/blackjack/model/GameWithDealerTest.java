package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.table.TableBox;
import org.junit.Assert;
import org.junit.Test;

public class GameWithDealerTest {

    /*@TestClient
    public void should_take_cards(){
        table table = (table)createTable();
        fillSomeBoxes(table);

        table.giveFirstCards();
        table.takeCards();
        for(TableBox box: table.getBoxes()){
            if (box.getHand().size() != 0) Assert.fail();
        }
    }*/

    @Test
    public void should_return_cards_value(){
        TableBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        Assert.assertEquals(18, GameWithDealer.getValue(box));

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card._8));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        Assert.assertEquals(15, GameWithDealer.getValue(box));

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        Assert.assertEquals(19, GameWithDealer.getValue(box));

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._8));
        Assert.assertEquals(17, GameWithDealer.getValue(box));

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card._10));
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, GameWithDealer.getValue(box));

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card.JACK));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, GameWithDealer.getValue(box));

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card.QUEEN));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, GameWithDealer.getValue(box));

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card.KING));
        Assert.assertEquals(GameWithDealer.BLACK_JACK, GameWithDealer.getValue(box));

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._5));
        box.giveCard(new Card(Card.CLUBS, Card._5));
        Assert.assertEquals(21, GameWithDealer.getValue(box));

        box.takeCards();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        Assert.assertEquals(12, GameWithDealer.getValue(box));
    }

    @Test
    public void should_ignore_hiden_card_in_sum(){
        TableBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(Card.HIDEN_CARD);
        Assert.assertEquals(11, GameWithDealer.getValue(box));
    }
}
