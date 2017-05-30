package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.table.deck.Card;
import org.junit.Assert;
import org.junit.Test;


public class TableBoxTest {

    @Test
    public void should_create_empty_hand() {
        IBox box = new TableBox();
        Assert.assertNotNull(box.getHand());
        Assert.assertEquals(0, box.getHand().length);
    }

    @Test
    public void should_give_cards(){
        IBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        Assert.assertEquals(2, box.getHand().length);
    }

    @Test
    public void should_take_cards(){
        IBox box = new TableBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        box.takeCards();
        Assert.assertEquals(0, box.getHand().length);
    }
}
