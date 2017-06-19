package nulp.pist21.blackjack.model.managers;

import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.Dealer;
import nulp.pist21.blackjack.model.Hand;
import nulp.pist21.blackjack.model.calculating.Combination;

public class PlayManager {

    private Hand[] hands;
    private Dealer dealer;
    private IDeck deck;
    private int index;

    public enum Actions{
        HIT,
        STAND
    }

    public PlayManager(IDeck deck, Dealer dealer) {
        this.deck = deck;
        this.dealer = dealer;
        this.hands = new Hand[0];
        index = 0;
    }

    public void start(int handCount){
        this.hands = new Hand[handCount + 1];
        for (int i = 0; i < handCount; i++) {
            hands[i] = new Hand();
        }
        hands[handCount] = dealer;
        dealer.takeCards();

        for(Hand hand: hands){
            hand.takeCards();
        }

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < handCount; j++){
                hands[j].giveCard(deck.next());
            }
        }
        dealer.giveCard(deck.next());
        dealer.giveCard(deck.next());
        index = -1;
        goToNextHand();
    }

    public boolean next(Actions action){
        if (index >= hands.length){
            return false;
        }

        if (action == Actions.HIT){
            hands[index].giveCard(deck.next());
            if (!(new Combination(hands[index]).canHit())){
                goToNextHand();
            }
        }
        return true;
    }

    public int getIndex() {
        return index;
    }

    public Hand[] getHands() {
        return hands;
    }

    public boolean isOver(){
        return index >= hands.length;
    }

    private void goToNextHand(){
        index++;
        while (index < hands.length - 1){
            if (new Combination(hands[index]).canHit()) break;
            index++;
        }
        //dealer step
        if (index == hands.length - 1){
            while (dealer.doStep(hands) == Actions.HIT){
                dealer.giveCard(deck.next());
            }
            index++;
        }
    }

    public Dealer getDealer() {
        return dealer;
    }

    public IDeck getDeck() {
        return deck;
    }
}
