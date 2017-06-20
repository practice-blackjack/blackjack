package nulp.pist21.blackjack.model.managers;

import nulp.pist21.blackjack.model.Hand;
import nulp.pist21.blackjack.model.calculating.Combination;

import static nulp.pist21.blackjack.model.calculating.Combination.BLACK_JACK;

public class WinManager {
    private double koefs[];

    public double[] start(Hand hands[], int dealerIndex){
        koefs = new double[hands.length];
        Combination dealersCombination = new Combination(hands[dealerIndex]);
        for(int i = 0; i < koefs.length; i++){
            koefs[i] = 0;
            if (i == dealerIndex) continue;
            Combination combination = new Combination(hands[i]);
            if (combination.isALot()){
                continue;
            }
            if (combination.getPoints() > dealersCombination.getPoints() || dealersCombination.isALot()){
                if (combination.getPoints() == BLACK_JACK){
                    koefs[i] = 3;
                    continue;
                }
                koefs[i] = 2;
                continue;
            }
            if (combination.getPoints() == dealersCombination.getPoints()){
                koefs[i] = 1;
            }
        }
        return koefs;
    }

    public double[] getKoefs() {
        return koefs;
    }
}
