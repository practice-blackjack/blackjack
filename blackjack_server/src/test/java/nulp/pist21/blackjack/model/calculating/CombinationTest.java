package nulp.pist21.blackjack.model.calculating;

import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.TurnableCard;
import nulp.pist21.blackjack.model.Hand;
import org.junit.Assert;
import org.junit.Test;

public class CombinationTest {

    @Test
    public void should_return_cards_value(){
        Hand hands[] = new Hand[6];
        for (int i = 0; i < hands.length; i++){
            hands[i] = new Hand();
        }

        hands[0].giveCard(new Card(Card.CLUBS, Card.ACE));
        hands[0].giveCard(new Card(Card.CLUBS, Card._7));       //18

        hands[1].giveCard(new Card(Card.CLUBS, Card._8));
        hands[1].giveCard(new Card(Card.CLUBS, Card._7));       //15

        hands[2].giveCard(new Card(Card.CLUBS, Card.ACE));
        hands[2].giveCard(new Card(Card.CLUBS, Card._7));
        hands[2].giveCard(new Card(Card.CLUBS, Card.ACE));      //19

        hands[3].giveCard(new Card(Card.CLUBS, Card.ACE));
        hands[3].giveCard(new Card(Card.CLUBS, Card._7));
        hands[3].giveCard(new Card(Card.CLUBS, Card.ACE));
        hands[3].giveCard(new Card(Card.CLUBS, Card._8));       //17

        hands[4].giveCard(new Card(Card.CLUBS, Card.KING));
        hands[4].giveCard(new Card(Card.CLUBS, Card.ACE));
        hands[4].giveCard(new Card(Card.CLUBS, Card.KING));     //21

        hands[5].giveCard(new Card(Card.CLUBS, Card.ACE));
        hands[5].giveCard(new Card(Card.CLUBS, Card._5));
        hands[5].giveCard(new Card(Card.CLUBS, Card._5));       //21


        Assert.assertEquals(18, new Combination(hands[0]).getPoints());
        Assert.assertEquals(15, new Combination(hands[1]).getPoints());
        Assert.assertEquals(19, new Combination(hands[2]).getPoints());
        Assert.assertEquals(17, new Combination(hands[3]).getPoints());
        Assert.assertEquals(21, new Combination(hands[4]).getPoints());
        Assert.assertEquals(21, new Combination(hands[5]).getPoints());
    }

    @Test
    public void should_return_black_jack(){
        Hand hands[] = new Hand[8];
        for (int i = 0; i < hands.length; i++){
            hands[i] = new Hand();
        }

        hands[0].giveCard(new Card(Card.CLUBS, Card.ACE));
        hands[0].giveCard(new Card(Card.CLUBS, Card._10));

        hands[1].giveCard(new Card(Card.CLUBS, Card.ACE));
        hands[1].giveCard(new Card(Card.CLUBS, Card.JACK));

        hands[2].giveCard(new Card(Card.CLUBS, Card.ACE));
        hands[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));

        hands[3].giveCard(new Card(Card.CLUBS, Card.ACE));
        hands[3].giveCard(new Card(Card.CLUBS, Card.KING));

        hands[4].giveCard(new Card(Card.CLUBS, Card._10));
        hands[4].giveCard(new Card(Card.CLUBS, Card.ACE));

        hands[5].giveCard(new Card(Card.CLUBS, Card.JACK));
        hands[5].giveCard(new Card(Card.CLUBS, Card.ACE));

        hands[6].giveCard(new Card(Card.CLUBS, Card.QUEEN));
        hands[6].giveCard(new Card(Card.CLUBS, Card.ACE));

        hands[7].giveCard(new Card(Card.CLUBS, Card.KING));
        hands[7].giveCard(new Card(Card.CLUBS, Card.ACE));


        Assert.assertEquals(Combination.BLACK_JACK, new Combination(hands[0]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(hands[1]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(hands[2]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(hands[3]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(hands[4]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(hands[5]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(hands[6]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(hands[7]).getPoints());
    }

    @Test
    public void should_return_a_lot(){
        Hand hands[] = new Hand[4];
        for (int i = 0; i < hands.length; i++){
            hands[i] = new Hand();
        }

        hands[0].giveCard(new Card(Card.CLUBS, Card.KING));
        hands[0].giveCard(new Card(Card.CLUBS, Card._7));
        hands[0].giveCard(new Card(Card.CLUBS, Card.KING));     //a lot(27)

        hands[1].giveCard(new Card(Card.CLUBS, Card._9));
        hands[1].giveCard(new Card(Card.CLUBS, Card._8));
        hands[1].giveCard(new Card(Card.CLUBS, Card._5));       // a lot(22)

        hands[2].giveCard(new Card(Card.CLUBS, Card.KING));
        hands[2].giveCard(new Card(Card.CLUBS, Card._8));
        hands[2].giveCard(new Card(Card.CLUBS, Card.QUEEN));       // a lot(28)

        hands[3].giveCard(new Card(Card.CLUBS, Card.KING));
        hands[3].giveCard(new Card(Card.CLUBS, Card._8));
        hands[3].giveCard(new Card(Card.CLUBS, Card._2));
        hands[3].giveCard(new Card(Card.CLUBS, Card.QUEEN));       // a lot(30)

        Assert.assertTrue(new Combination(hands[0]).isALot());
        Assert.assertTrue(new Combination(hands[1]).isALot());
        Assert.assertTrue(new Combination(hands[2]).isALot());
        Assert.assertTrue(new Combination(hands[3]).isALot());
    }

    @Test
    public void should_ignore_hidden_card_in_sum(){
        Hand[] hands = new Hand[]{
                new Hand(),
        };

        hands[0].giveCard(new TurnableCard(new Card(Card.CLUBS, Card.ACE)));
        hands[0].giveCard(new Card(Card.HEARTS, Card._9));

        Assert.assertEquals(9, new Combination(hands[0]).getPoints());
    }

    @Test
    public void should_return_loose_kof_if_a_lot(){
        final double looseKof = 0;
        Assert.assertEquals(looseKof,
                new Combination(28).getWin(
                new Combination(21)), 0);

        Assert.assertEquals(looseKof,
                new Combination(22).getWin(
                new Combination(2)), 0);

        Assert.assertEquals(looseKof,
                new Combination(31).getWin(
                new Combination(Combination.BLACK_JACK)), 0);
    }

    @Test
    public void should_return_draw_kof_if_points_equals(){
        final double drawKof = 1;
        Assert.assertEquals(drawKof,
                new Combination(2).getWin(
                new Combination(2)), 0);

        Assert.assertEquals(drawKof,
                new Combination(21).getWin(
                new Combination(21)), 0);

        Assert.assertEquals(drawKof,
                new Combination(Combination.BLACK_JACK).getWin(
                new Combination(Combination.BLACK_JACK)), 0);
    }

    @Test
    public void should_return_3_to_2_kof_if_black_jack(){
        final double _3_to_2_kof = 2.2;
        Assert.assertEquals(_3_to_2_kof,
                new Combination(Combination.BLACK_JACK).getWin(
                new Combination(2)), 0);

        Assert.assertEquals(_3_to_2_kof,
                new Combination(Combination.BLACK_JACK).getWin(
                new Combination(21)), 0);

        Assert.assertEquals(_3_to_2_kof,
                new Combination(Combination.BLACK_JACK).getWin(
                new Combination(22)), 0);
    }

}
