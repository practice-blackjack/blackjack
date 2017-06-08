package nulp.pist21.blackjack.model.game;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.IDeck;

public class Round implements IRound {
    private IHand[] players;
    private Dealer dealer;
    private IDeck deck;
    private int currentIndex;

    public static final int DEALER_INDEX = Integer.MAX_VALUE;

    public Round(IHand[] players, IDeck deck) {
        this.players = players;
        this.deck = deck;
        dealer = new Dealer();
        currentIndex = -1;
    }

    @Override
    public void start(){
        for(IHand playerBox: players){
            playerBox.takeCards();
        }

        for(int i = 0; i < 2; i++){
            for(IHand player: players){
                player.giveCard(deck.next());
            }
        }
        dealer.giveCard(deck.next());
        dealer.giveCard(deck.next());
        currentIndex = getNextIndex();
    }

    @Override
    public boolean next(GameAction action, IDeck deck){ //false if round over
        if (currentIndex < players.length){
            if (action.getAction() == GameAction.Actions.HIT && new Combination(players[currentIndex]).canHit()){
                players[currentIndex].giveCard(deck.next());
            }
            else {
                currentIndex = getNextIndex();
            }
        }

        //dealer step
        if (currentIndex == players.length){
            while (dealer.doStep(this, DEALER_INDEX).getAction() == GameAction.Actions.HIT){
                dealer.giveCard(deck.next());
            }
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

    private int getNextIndex(){
        int index = currentIndex + 1;
        while (index < players.length){
            if (new Combination(players[index]).canHit()) break;
            index++;
        }
        return index;
    }

}
