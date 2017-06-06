package nulp.pist21.blackjack.model.game;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.IDeck;

public class GameWithDealer implements IGame {
    private IHand[] players;
    private Dealer dealer;

    private int currentIndex;

    public static final int DEALER_INDEX = Integer.MAX_VALUE;

    public GameWithDealer() {
        this.dealer = new Dealer();
    }

    @Override
    public void start(IHand[] playingBoxes, IDeck deck){
        this.players = playingBoxes;
        for(IHand playingBox: playingBoxes){
            playingBox.takeCards();
        }

        for(int i = 0; i < 2; i++){
            for(IHand playingBox: playingBoxes){
                playingBox.giveCard(deck.next());
            }
        }
        dealer.giveCard(deck.next());
        dealer.giveCard(deck.next());
        currentIndex = 0;
    }

    @Override
    public boolean next(GameAction action, IDeck deck){ //false if round over
        if (currentIndex < players.length){
            if (action.getAction() == GameAction.Actions.HIT){
                players[currentIndex].giveCard(deck.next());
            }
            else {
                currentIndex++;
            }
        }

        //dealer step
        if (currentIndex == players.length){
            while (dealer.doStep(this, DEALER_INDEX).getAction() != GameAction.Actions.STAND){
                dealer.giveCard(deck.next());
            }
            currentIndex++;
        }
        if (currentIndex > players.length){
            return false;
        }
        return true;
    }

    @Override
    public void end(){
        for (IHand box: players){
            box.takeCards();
        }
        dealer.takeCards();
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public IHand getPlayer(int index) {
        if (index == DEALER_INDEX){
            return dealer;
        }
        if (index >= 0 && index < players.length){
            return players[index];
        }
        return null;
    }

    @Override
    public int getPlayerCount(){
        return players.length;
    }



    public static class Combination {

        public static final int BLACK_JACK = Integer.MAX_VALUE;

        public static double getWin(IHand first, IHand second) {
            int firstPoints = getPoints(first);
            int secondPoints = getPoints(second);

            if (!isALot(firstPoints)){
                if (getPoints(first) > getPoints(second) || isALot(secondPoints)){
                    if (getPoints(first) == BLACK_JACK){
                        return 2.5;
                    }
                    return 2;
                }
                else if (getPoints(first) == getPoints(second)){
                    return 1;
                }
            }
            return 0;
        }

        public static boolean isALot(int points) {
            return points > 21 && points != BLACK_JACK;
        }

        public static boolean isALot(IHand hand) {
            return isALot(getPoints(hand));
        }

        public static int getPoints(IHand hand) {
            int points = 0;

            int aces = 0;

            for(Card calcCard: hand.getHand()){
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
                    hand.getHand().length == 2){
                return BLACK_JACK;
            }
            return points;
        }
    }
}
