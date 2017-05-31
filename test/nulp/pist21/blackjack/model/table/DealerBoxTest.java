package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.table.deck.Card;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class DealerBoxTest {

    @Test
    public void should_create_empty_hand() {
        IBox box = new DealerBox();
        Assert.assertNotNull(box.getHand());
        Assert.assertEquals(0, box.getHand().length);
    }

    @Test
    public void should_give_cards(){
        IBox box = new DealerBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        Assert.assertEquals(2, box.getHand().length);
    }

    @Test
    public void should_take_cards(){
        IBox box = new DealerBox();
        box.giveCard(new Card(Card.CLUBS, Card.ACE));
        box.giveCard(new Card(Card.CLUBS, Card._7));
        box.takeCards();
        Assert.assertEquals(0, box.getHand().length);
    }

    @Test
    public void should_hide_first_card(){
        IBox box = new DealerBox();
        box.giveCard(new Card(Card.CLUBS, Card._5));
        box.giveCard(new Card(Card.CLUBS, Card.ACE));

        Assert.assertEquals(2, box.getHand().length);
        Assert.assertEquals(Card.HIDDEN_CARD, box.getHand()[0]);
    }

    @Test
    public void should_open_cards(){
        IBox box = new DealerBox();
        box.giveCard(new Card(Card.CLUBS, Card._5));
        box.giveCard(new Card(Card.CLUBS, Card.ACE));

        ((DealerBox)box).openCard();

        Assert.assertFalse(Arrays.stream(box.getHand()).anyMatch(c -> c == Card.HIDDEN_CARD));
    }
}
