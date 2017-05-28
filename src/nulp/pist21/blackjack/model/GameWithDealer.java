package nulp.pist21.blackjack.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GameWithDealer {
    private TableBox dealerBox;
    private Dealer dealer;
    private TableBox[] playingBoxes;

    public GameWithDealer(TableBox[] playingBoxes) {
        this.playingBoxes = playingBoxes;
        this.dealerBox = new TableBox();
        this.dealerBox.sitDown(dealer);
    }

    public void giveFirstCards(IDeck deck){
        for (TableBox box: playingBoxes){
            for (int i = 0; i < 2; i++) {
                box.giveCard(deck.next());
            }
        }

        for (int i = 0; i < 2; i++) {
            dealerBox.giveCard(deck.next());
        }
    }

    public static int getValue(TableBox box){
        int sum = 0;

        ArrayList<Card> aces = new ArrayList<>();
        for(Card card: box.getHand()){
            if (card.getValue() == Card.ACE){
                aces.add(card);
            }
            else if (card.getValue() >= Card._10 && card.getValue() <= Card.KING){
                sum += 10;
            }
            else {
                sum += card.getValue() + 1;
            }
        }
        for (Card ace: aces) {
            if (sum + 11 <= 21){
                sum += 11;
            }
            else sum += 1;
        }
        return sum;
    }

    public List<Pair<TableBox, Float>> getWinners(){
        List<Pair<TableBox, Float>> winners = new ArrayList<>();
        for (TableBox box: playingBoxes){
            if (getValue(box) > getValue(dealerBox)){
                winners.add(new Pair<>(box, 2f));
            }
            else if (getValue(box) == getValue(dealerBox)){
                winners.add(new Pair<>(box, 1f));
            }
        }
        return winners;
    }
}
