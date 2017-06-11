package nulp.pist21.blackjack.model.game.round;

import nulp.pist21.blackjack.model.actions.Action;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.game.calculating.Combination;
import nulp.pist21.blackjack.model.game.Dealer;
import nulp.pist21.blackjack.model.game.IHand;

public class GameRound implements IRound {
    private IHand[] hands;
    private Dealer dealer;
    private IDeck deck;
    private int index;

    public static final int DEALER_INDEX = Integer.MAX_VALUE;

    public GameRound(IHand hands[], IDeck deck, Dealer dealer) {
        this.hands = hands;
        this.deck = deck;
        this.dealer = dealer;

        index = -1;

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

    @Override
    public boolean next(Action action){
        if (index >= hands.length){
            return false;
        }
        if (!(action instanceof GameAction)){
            return false;
        }
        GameAction gameAction = (GameAction) action;

        if (gameAction.getAction() == GameAction.Actions.HIT){
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
            while (dealer.doStep(this, DEALER_INDEX).getAction() == GameAction.Actions.HIT){
                dealer.giveCard(deck.next());
            }
        }
        return true;
    }

    @Override
    public void end(){
        for (IHand box: hands){
            box.takeCards();
        }
        dealer.takeCards();
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public boolean isEnd(){
        return index >= hands.length;
    }

    private void goToNextHand(){
        index++;
        while (index < hands.length){
            if (new Combination(hands[index]).canHit()) break;
            index++;
        }
    }

    public IHand[] getHands() {
        return hands;
    }

    public Dealer getDealer() {
        return dealer;
    }
}
