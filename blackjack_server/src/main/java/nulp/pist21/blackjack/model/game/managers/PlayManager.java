package nulp.pist21.blackjack.model.game.managers;

import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.game.Dealer;
import nulp.pist21.blackjack.model.game.IHand;
import nulp.pist21.blackjack.model.game.calculating.Combination;

public class PlayManager {

    private IHand[] hands;
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
        index = -1;
    }

    public void start(IHand hands[]){
        this.hands = hands;

        for(IHand playerBox: hands){
            playerBox.takeCards();
        }

        for(int i = 0; i < 2; i++){
            for(IHand player: hands){
                player.giveCard(deck.next());
            }
        }
        dealer.giveCard(deck.next());
        dealer.giveCard(deck.next());
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
        else {
            goToNextHand();
        }

        //dealer step
        if (index == hands.length){
            while (dealer.doStep(hands) == Actions.HIT){
                dealer.giveCard(deck.next());
            }
        }
        return true;
    }

    public void end(){
        for (IHand box: hands){
            box.takeCards();
        }
        dealer.takeCards();
    }

    public IHand getCurrentHand() {
        return hands[index];
    }

    public boolean isOver(){
        return index >= hands.length;
    }

    private void goToNextHand(){
        index++;
        while (index < hands.length){
            if (new Combination(hands[index]).canHit()) break;
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
