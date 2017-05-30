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

public class GameWithDealer implements IGame {
    private IBox[] playingBoxes;
    private DealerBox dealerBox;
    private int dealerIndex;
    private IDeck deck;

    private int currentIndex;

    public static final int BLACK_JACK = Integer.MAX_VALUE;
    public static final int A_LOT = BLACK_JACK - 1;

    public GameWithDealer(IDeck deck) {
        this.deck = deck;
        this.playingBoxes = new TableBox[]{};
        this.dealerBox = new DealerBox();
    }

    @Override
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

    @Override
    public boolean next(GameAction action){ //false if round over
        if (action.getAction() == GameAction.Actions.HIT){
            playingBoxes[currentIndex].giveCard(deck.next());
        }
        else {
            currentIndex++;
        }

        //dealer step
        if (currentIndex == dealerIndex){
            while (getValue(dealerIndex) <= 16){
                playingBoxes[dealerIndex].giveCard(deck.next());
            }
            currentIndex++;
            return false;
        }
        return true;
    }

    @Override
    public List<Pair<IBox, Float>> end(){
        List<Pair<IBox, Float>> winners = getWinners();
        for (IBox box: playingBoxes){
            box.takeCards();
        }
        return winners;
    }

    @Override
    public int getValue(int index){
        int points = 0;

        int aces = 0;
        for(Card card: playingBoxes[index].getHand()){
            if (card.getValue() == Card.ACE){
                aces++;
            }
            else if (card.getValue() >= Card._10 && card.getValue() <= Card.KING){
                points += 10;
            }
            else if (card != Card.HIDDEN_CARD){
                points += card.getValue() + 1;
            }
        }
        for (int i = 0; i < aces; i++) {
            if (points + 11 <= 21){
                points += 11;
            }
            else points += 1;
        }

        if (points > 21){
            return A_LOT;
        }
        if (points == 21 &&
                playingBoxes[index].getHand().length == 2){
            return BLACK_JACK;
        }

        return points;
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

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public IBox[] getPlayingBoxes() {
        return playingBoxes;
    }

    public int getDealerIndex() {
        return dealerIndex;
    }
}
