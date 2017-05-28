package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.Deck.Card;
import nulp.pist21.blackjack.model.Table.TableBox;
import org.junit.Assert;
import org.junit.Test;

import static nulp.pist21.blackjack.model.Deck.Card.CLUBS;

public class GameWithDealerTest {

    /*@Test
    public void should_take_cards(){
        Table table = (Table)createTable();
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
        box.giveCard(new Card(CLUBS, Card._7));
        if (GameWithDealer.getValue(box) != 18) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(CLUBS, Card._8));
        box.giveCard(new Card(CLUBS, Card._7));
        if (GameWithDealer.getValue(box) != 15) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(CLUBS, Card.ACE));
        box.giveCard(new Card(CLUBS, Card._7));
        box.giveCard(new Card(CLUBS, Card.ACE));
        if (GameWithDealer.getValue(box) != 19) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(CLUBS, Card.ACE));
        box.giveCard(new Card(CLUBS, Card._7));
        box.giveCard(new Card(CLUBS, Card.ACE));
        box.giveCard(new Card(CLUBS, Card._8));
        if (GameWithDealer.getValue(box) != 17) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(CLUBS, Card._10));
        box.giveCard(new Card(CLUBS, Card.ACE));
        if (GameWithDealer.getValue(box) != GameWithDealer.BLACK_JACK) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(CLUBS, Card.ACE));
        box.giveCard(new Card(CLUBS, Card.JACK));
        if (GameWithDealer.getValue(box) != GameWithDealer.BLACK_JACK) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(CLUBS, Card.ACE));
        box.giveCard(new Card(CLUBS, Card.QUEEN));
        if (GameWithDealer.getValue(box) != GameWithDealer.BLACK_JACK) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(CLUBS, Card.ACE));
        box.giveCard(new Card(CLUBS, Card.KING));
        if (GameWithDealer.getValue(box) != GameWithDealer.BLACK_JACK) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(CLUBS, Card.ACE));
        box.giveCard(new Card(CLUBS, Card._5));
        box.giveCard(new Card(CLUBS, Card._5));
        if (GameWithDealer.getValue(box) != 21) Assert.fail();

        box.takeCards();
        box.giveCard(new Card(CLUBS, Card.ACE));
        box.giveCard(new Card(CLUBS, Card.ACE));
        if (GameWithDealer.getValue(box) != 12) Assert.fail();
    }
}
