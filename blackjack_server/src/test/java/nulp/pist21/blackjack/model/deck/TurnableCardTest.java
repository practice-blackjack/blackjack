package nulp.pist21.blackjack.model.deck;

import org.junit.Assert;
import org.junit.Test;

public class TurnableCardTest {
    @Test
    public void should_close_card_on_creating(){
        TurnableCard card = new TurnableCard(Card.CLUBS, Card.ACE);
        Assert.assertEquals(Card.UNDEFINED_SUIT, card.getSuit());
        Assert.assertEquals(Card.UNDEFINED_VALUE, card.getValue());
    }

    @Test
    public void should_open_card(){
        TurnableCard card = new TurnableCard(Card.CLUBS, Card.ACE);
        card.open();
        Assert.assertEquals(Card.CLUBS, card.getSuit());
        Assert.assertEquals(Card.ACE, card.getValue());
    }

    @Test
    public void should_close_card_after_opening(){
        TurnableCard card = new TurnableCard(Card.CLUBS, Card.ACE);
        card.open();
        card.close();
        Assert.assertEquals(Card.UNDEFINED_SUIT, card.getSuit());
        Assert.assertEquals(Card.UNDEFINED_VALUE, card.getValue());
    }

    @Test
    public void should_open_card_after_closing(){
        TurnableCard card = new TurnableCard(Card.CLUBS, Card.ACE);
        card.open();
        card.close();
        card.open();
        Assert.assertEquals(Card.CLUBS, card.getSuit());
        Assert.assertEquals(Card.ACE, card.getValue());
    }
}
