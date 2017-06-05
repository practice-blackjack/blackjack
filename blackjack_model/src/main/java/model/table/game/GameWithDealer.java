package model.table.game;

import javafx.util.Pair;
import model.actions.GameAction;
import model.table.deck.Card;
import model.table.deck.IDeck;
import model.table.TableBox;

import java.util.ArrayList;
import java.util.List;

public class GameWithDealer implements IGame {
    private TableBox[] playingBoxes;
    private TableBox dealerBox;
    private IDeck deck;
    private Card hiddenCard;

    private int currentIndex;

    public static final int DEALER_INDEX = Integer.MAX_VALUE;

    public static final int BLACK_JACK = Integer.MAX_VALUE;
    public static final int A_LOT = BLACK_JACK - 1;

    public GameWithDealer(IDeck deck) {
        this.deck = deck;
        this.playingBoxes = new TableBox[]{};
        this.dealerBox = new TableBox();
    }

    @Override
    public void start(TableBox[] playingBoxes){
        this.playingBoxes = playingBoxes;
        for(TableBox playingBox: playingBoxes){
            playingBox.takeCards();
        }

        for(int i = 0; i < 2; i++){
            for(TableBox playingBox: playingBoxes){
                playingBox.giveCard(deck.next());
            }
        }

        hiddenCard = deck.next();
        dealerBox.giveCard(Card.HIDDEN_CARD);
        dealerBox.giveCard(deck.next());
        currentIndex = 0;
    }

    @Override
    public boolean next(GameAction action){ //false if round over
        if (currentIndex < playingBoxes.length){
            if (action.getAction() == GameAction.Actions.HIT){
                playingBoxes[currentIndex].giveCard(deck.next());
            }
            else {
                currentIndex++;
            }
        }

        //dealer step
        if (currentIndex == playingBoxes.length){
            openCard();
            GameAction dealerAction;
            do{
                dealerAction = deallerStep();
            }
            while (dealerAction.getAction() != GameAction.Actions.STAND);
            currentIndex++;
        }
        if (currentIndex > playingBoxes.length){
            return false;
        }
        return true;
    }

    private GameAction deallerStep(){
        if (getValue(DEALER_INDEX) <= 16){
            dealerBox.giveCard(deck.next());
            return new GameAction(GameAction.Actions.HIT);
        }
        return new GameAction(GameAction.Actions.STAND);
    }

    private void openCard(){
        Card opennedCard = dealerBox.getCard(1);
        dealerBox.takeCards();
        dealerBox.giveCard(hiddenCard);
        hiddenCard = null;
        dealerBox.giveCard(opennedCard);
    }

    @Override
    public List<Pair<TableBox, Float>> end(){
        List<Pair<TableBox, Float>> winners = getWinners();
        for (TableBox box: playingBoxes){
            box.takeCards();
        }
        dealerBox.takeCards();
        return winners;
    }

    @Override
    public int getValue(int index){
        int points = 0;

        int aces = 0;

        TableBox calcBox;
        if (index == DEALER_INDEX){
            calcBox = dealerBox;
        }
        else {
            calcBox = playingBoxes[index];
        }

        for(int i = 0; i < calcBox.getCardsCount(); i++){
            Card calcCard = calcBox.getCard(i);
            if (calcCard.getValue() == Card.ACE){
                aces++;
            }
            else if (calcCard.getValue() >= Card._10 && calcCard.getValue() <= Card.KING){
                points += 10;
            }
            else if (calcCard != Card.HIDDEN_CARD){
                points += calcCard.getValue() + 1;
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
                playingBoxes[index].getCardsCount() == 2){
            return BLACK_JACK;
        }

        return points;
    }

    public List<Pair<TableBox, Float>> getWinners(){
        List<Pair<TableBox, Float>> winners = new ArrayList<>();
        for (int i = 0; i < playingBoxes.length; i++){
            if (getValue(i) > getValue(DEALER_INDEX)){
                winners.add(new Pair<>(playingBoxes[i], 2f));
            }
            else if (getValue(i) == getValue(DEALER_INDEX)){
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
    public TableBox getBox(int index) {
        if (index == DEALER_INDEX){
            return dealerBox;
        }
        if (index >= 0 && index < playingBoxes.length){
            return playingBoxes[index];
        }
        return null;
    }

    @Override
    public int getBoxCount(){
        return playingBoxes.length;
    }
}
