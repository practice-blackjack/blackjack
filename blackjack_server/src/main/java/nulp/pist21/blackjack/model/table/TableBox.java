package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.game.IHand;

import java.util.ArrayList;
import java.util.List;

public class TableBox implements IHand{

    private boolean isActivated;
    private List<Card> hand;

    public TableBox() {
        hand = new ArrayList<>();
        isActivated = false;
    }

    @Override
    public void giveCard(Card card){
        hand.add(card);
    }

    @Override
    public void takeCards(){
        hand.clear();
    }

    @Override
    public Card[] getHand(){
        return hand.toArray(new Card[hand.size()]);
    }

    public boolean isActivated(){
        return isActivated;
    }

    public void isActivated(boolean activated){
        isActivated = activated;
        if (!isActivated) hand.clear();
    }
}