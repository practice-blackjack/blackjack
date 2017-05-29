package nulp.pist21.blackjack.model;

import javafx.util.Pair;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.table.TableBox;

import java.util.ArrayList;
import java.util.List;

public class GameWithDealer {
    private Dealer dealer;
    private TableBox[] playingBoxes;

    private int currentHand;

    public static final int BLACK_JACK = Integer.MAX_VALUE;

    public GameWithDealer(Dealer dealer) {
        currentHand = 0;
        this.playingBoxes = new TableBox[]{};
        this.dealer = new Dealer();
    }

    public void start(TableBox[] playingBoxes){
        this.playingBoxes = playingBoxes;
    }

    public void giveFirstCards(IDeck deck){
        for (int i = 0; i < 2; i++) {
            for (TableBox box: playingBoxes) {
                box.giveCard(deck.next());
            }
            dealer.giveCard(deck.next());
        }
    }

    public static int getValue(IHand hand){
        int sum = 0;

        ArrayList<Card> aces = new ArrayList<>();
        for(Card card: hand.getHand()){
            if (card.getValue() == Card.ACE){
                aces.add(card);
            }
            else if (card.getValue() >= Card._10 && card.getValue() <= Card.KING){
                sum += 10;
            }
            else if (card != Card.HIDEN_CARD){
                sum += card.getValue() + 1;
            }
        }
        for (Card ace: aces) {
            if (sum + 11 <= 21){
                sum += 11;
            }
            else sum += 1;
        }

        if (sum == 21 &&
            hand.getHand().size() == 2){
            return BLACK_JACK;
        }
        return sum;
    }

    public List<Pair<TableBox, Float>> getWinners(){
        List<Pair<TableBox, Float>> winners = new ArrayList<>();
        for (TableBox box: playingBoxes){
            if (getValue(box) > getValue(dealer)){
                winners.add(new Pair<>(box, 2f));
            }
            else if (getValue(box) == getValue(dealer)){
                winners.add(new Pair<>(box, 1f));
            }
        }
        return winners;
    }

    public int getCurrentHand() {
        return currentHand;
    }
}
