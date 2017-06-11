package nulp.pist21.blackjack.model.calculating;

import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.TurnableCard;
import nulp.pist21.blackjack.model.game.calculating.Combination;
import nulp.pist21.blackjack.model.table.TableBox;
import org.junit.Assert;
import org.junit.Test;

public class CombinationTest {

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


        Assert.assertEquals(18, new Combination(boxes[0]).getPoints());
        Assert.assertEquals(15, new Combination(boxes[1]).getPoints());
        Assert.assertEquals(19, new Combination(boxes[2]).getPoints());
        Assert.assertEquals(17, new Combination(boxes[3]).getPoints());
        Assert.assertEquals(21, new Combination(boxes[4]).getPoints());
        Assert.assertEquals(21, new Combination(boxes[5]).getPoints());
    }

    @Test
    public void should_return_black_jack(){
        TableBox boxes[] = new TableBox[8];
        for (int i = 0; i < boxes.length; i++){
            boxes[i] = new TableBox();
        }

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


        Assert.assertEquals(Combination.BLACK_JACK, new Combination(boxes[0]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(boxes[1]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(boxes[2]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(boxes[3]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(boxes[4]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(boxes[5]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(boxes[6]).getPoints());
        Assert.assertEquals(Combination.BLACK_JACK, new Combination(boxes[7]).getPoints());
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

        Assert.assertTrue(new Combination(boxes[0]).isALot());
        Assert.assertTrue(new Combination(boxes[1]).isALot());
        Assert.assertTrue(new Combination(boxes[2]).isALot());
        Assert.assertTrue(new Combination(boxes[3]).isALot());
    }

    @Test
    public void should_ignore_hidden_card_in_sum(){
        TableBox[] boxes = new TableBox[]{
                new TableBox(),
        };

        boxes[0].giveCard(new TurnableCard(new Card(Card.CLUBS, Card.ACE)));
        boxes[0].giveCard(new Card(Card.HEARTS, Card._9));

        Assert.assertEquals(9, new Combination(boxes[0]).getPoints());
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
