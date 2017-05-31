package nulp.pist21.blackjack.model.table.game;

import javafx.util.Pair;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.deck.Card;
import nulp.pist21.blackjack.model.table.deck.IDeck;
import nulp.pist21.blackjack.model.table.TableBox;

import java.util.ArrayList;
import java.util.List;

public class GameWithDealer implements IGame {
    private TableBox[] playingBoxes;
    private int dealerIndex;
    private IDeck deck;
    private Card hiddenCard;

    private int currentIndex;

    public static final int BLACK_JACK = Integer.MAX_VALUE;
    public static final int A_LOT = BLACK_JACK - 1;

    public GameWithDealer(IDeck deck) {
        this.deck = deck;
        this.playingBoxes = new TableBox[]{};
    }

    @Override
    public void start(TableBox[] playingBoxes){
        this.dealerIndex = playingBoxes.length;
        this.playingBoxes = new TableBox[dealerIndex + 1];
        for (int i = 0; i < dealerIndex; i++){
            this.playingBoxes[i] = playingBoxes[i];
        }
        this.playingBoxes[dealerIndex] = new TableBox();

        //gives first card
        for (int boxIndex = 0; boxIndex < dealerIndex; boxIndex++) {
            this.playingBoxes[boxIndex].giveCard(deck.next());
        }
        //hide dealers first card
        hiddenCard = deck.next();
        this.playingBoxes[dealerIndex].giveCard(Card.HIDDEN_CARD);

        //gives second card
        for (int boxIndex = 0; boxIndex < dealerIndex; boxIndex++) {
            this.playingBoxes[boxIndex].giveCard(deck.next());
        }
        this.playingBoxes[dealerIndex].giveCard(deck.next());

        currentIndex = 0;
    }

    @Override
    public boolean next(GameAction action){ //false if round over
        if (currentIndex < dealerIndex){
            if (action.getAction() == GameAction.Actions.HIT){
                playingBoxes[currentIndex].giveCard(deck.next());
            }
            else {
                currentIndex++;
            }
        }

        //dealer step
        if (currentIndex == dealerIndex){
            openCard();
            GameAction dealerAction;
            do{
                dealerAction = deallerStep();
            }
            while (dealerAction.getAction() != GameAction.Actions.STAND);
            currentIndex++;
        }
        if (currentIndex > dealerIndex){
            return false;
        }
        return true;
    }

    private GameAction deallerStep(){
        if (getValue(dealerIndex) <= 16){
            playingBoxes[dealerIndex].giveCard(deck.next());
            return new GameAction(GameAction.Actions.HIT);
        }
        return new GameAction(GameAction.Actions.STAND);
    }

    private void openCard(){
        Card opennedCard = playingBoxes[dealerIndex].getHand()[1];
        playingBoxes[dealerIndex].takeCards();
        playingBoxes[dealerIndex].giveCard(hiddenCard);
        hiddenCard = null;
        playingBoxes[dealerIndex].giveCard(opennedCard);
    }

    @Override
    public List<Pair<TableBox, Float>> end(){
        List<Pair<TableBox, Float>> winners = getWinners();
        for (TableBox box: playingBoxes){
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

    public List<Pair<TableBox, Float>> getWinners(){
        List<Pair<TableBox, Float>> winners = new ArrayList<>();
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
    public TableBox[] getPlayingBoxes() {
        return playingBoxes;
    }

    public int getDealerIndex() {
        return dealerIndex;
    }
}
