package nulp.pist21.blackjack.model.game;

import nulp.pist21.blackjack.model.deck.Card;

public class Combination {
    public static final int BLACK_JACK = Integer.MAX_VALUE;

    private int points;

    public Combination(IHand hand) {
        points = 0;

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
            points = BLACK_JACK;
        }
    }

    public Combination(int points){
        this.points = points;
    }

    public double getWin(Combination other) {
        if (!isALot()){
            if (this.points > other.points || other.isALot()){
                if (this.points == BLACK_JACK){
                    return 2.2;
                }
                return 2;
            }
            else if (this.points == other.points){
                return 1;
            }
        }
        return 0;
    }

    public boolean isALot() {
        return points > 21 && points != BLACK_JACK;
    }

    public boolean canHit(){
        return points < 21 && points != BLACK_JACK;
    }

    public int getPoints() {
        return points;
    }
}
