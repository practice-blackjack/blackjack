package nulp.pist21.blackjack.model.table.game;

import javafx.util.Pair;
import nulp.pist21.blackjack.model.table.IBox;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.DealerBox;
import nulp.pist21.blackjack.model.table.deck.Card;
import nulp.pist21.blackjack.model.table.deck.IDeck;
import nulp.pist21.blackjack.model.table.TableBox;

import java.util.ArrayList;
import java.util.List;

public class GameWithDealer {
    private IBox[] playingBoxes;
    private DealerBox dealerBox;
    private int dealerIndex;
    private IDeck deck;

    private int currentIndex;

    public static final int BLACK_JACK = Integer.MAX_VALUE;

    public GameWithDealer(IDeck deck) {
        this.deck = deck;
        this.playingBoxes = new TableBox[]{};
        this.dealerBox = new DealerBox();
    }

    public void start(IBox[] playingBoxes){
        this.dealerIndex = playingBoxes.length;
        this.playingBoxes = new IBox[dealerIndex + 1];
        for (int i = 0; i < dealerIndex; i++){
            this.playingBoxes[i] = playingBoxes[i];
        }
        this.playingBoxes[dealerIndex] = dealerBox;

        for (int i = 0; i < 2; i++) {
            for (IBox box: this.playingBoxes) {
                box.giveCard(deck.next());
            }
        }
        currentIndex = 0;
    }

    public boolean next(GameAction action){ //false if round over
        if (action.getAction() == GameAction.Actions.HIT){
            playingBoxes[currentIndex].giveCard(deck.next());
        }
        else {
            currentIndex++;
        }

        //dealer step
        if (currentIndex == dealerIndex){
            while (getValue(dealerIndex) >= 16){
                playingBoxes[dealerIndex].takeCards();
            }
            currentIndex++;
            return false;
        }
        return true;
    }

    public void end(){
        for (IBox box: playingBoxes){
            box.takeCards();
        }
    }

    public int getValue(int index){
        int sum = 0;

        int aces = 0;
        for(Card card: playingBoxes[index].getHand()){
            if (card.getValue() == Card.ACE){
                aces++;
            }
            else if (card.getValue() >= Card._10 && card.getValue() <= Card.KING){
                sum += 10;
            }
            else if (card != Card.HIDDEN_CARD){
                sum += card.getValue() + 1;
            }
        }
        for (int i = 0; i < aces; i++) {
            if (sum + 11 <= 21){
                sum += 11;
            }
            else sum += 1;
        }

        if (sum == 21 &&
                playingBoxes[index].getHand().length == 2){
            return BLACK_JACK;
        }
        return sum;
    }

    public List<Pair<IBox, Float>> getWinners(){
        List<Pair<IBox, Float>> winners = new ArrayList<>();
        for (int i = 0; i < dealerIndex; i++){
            if (getValue(i) > getValue(dealerIndex)){
                winners.add(new Pair<>(playingBoxes[i], 2f));
            }
            else if (getValue(i) == getValue(dealerIndex)){
                winners.add(new Pair<>(playingBoxes[i], 1f));
            }
        }
        return winners;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public IBox[] getPlayingBoxes() {
        return playingBoxes;
    }

    public int getDealerIndex() {
        return dealerIndex;
    }
}
