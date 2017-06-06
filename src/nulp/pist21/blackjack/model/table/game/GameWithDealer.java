package nulp.pist21.blackjack.model.table.game;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.deck.Card;
import nulp.pist21.blackjack.model.table.deck.IDeck;
import nulp.pist21.blackjack.model.table.TableBox;
import nulp.pist21.blackjack.model.table.deck.TurnableCard;

public class GameWithDealer implements IGame {
    private TableBox[] playingBoxes;
    private TableBox dealerBox;
    private IDeck deck;
    private TurnableCard hiddenCard;

    private int currentIndex;

    public static final int DEALER_INDEX = Integer.MAX_VALUE;

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
        this.hiddenCard = new TurnableCard(deck.next());
        dealerBox.giveCard(this.hiddenCard);
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
            hiddenCard.open();
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
        if (Combination.getPoints(dealerBox.getHand()) <= 16){
            dealerBox.giveCard(deck.next());
            return new GameAction(GameAction.Actions.HIT);
        }
        return new GameAction(GameAction.Actions.STAND);
    }

    @Override
    public void end(){
        for (TableBox box: playingBoxes){
            box.takeCards();
        }
        dealerBox.takeCards();
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



    public static class Combination {

        public static final int BLACK_JACK = Integer.MAX_VALUE;

        public static double getWin(Card first[], Card second[]) {
            if (getPoints(first) > getPoints(second)){
                if (getPoints(first) == BLACK_JACK){
                    return 2.5;
                }
                return 2;
            }
            else if (getPoints(first) == getPoints(second)){
                return 1;
            }
            return 0;
        }

        public static boolean IsALot(Card hand[]) {
            int points = getPoints(hand);
            return points > 21 && points != BLACK_JACK;
        }

        public static int getPoints(Card hand[]) {
            int points = 0;

            int aces = 0;

            for(Card calcCard: hand){
                if (calcCard.getValue() == Card.ACE){
                    aces++;
                }
                else if (calcCard.getValue() >= Card._10 && calcCard.getValue() <= Card.KING){
                    points += 10;
                }
                else if (calcCard.getValue() != Card.UNDEFINED_VALUE){
                    points += calcCard.getValue() + 1;
                }
            }
            for (int i = 0; i < aces; i++) {
                if (points + 11 <= 21){
                    points += 11;
                }
                else points += 1;
            }

            if (points == 21 &&
                    hand.length == 2){
                return BLACK_JACK;
            }
            return points;
        }
    }
}
