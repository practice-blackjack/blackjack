package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.table.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class TableBox implements IBox {

    private List<Card> hand;

    public TableBox() {
        hand = new ArrayList<>();
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
    public Card[] getHand() {
        return hand.toArray(new Card[hand.size()]);
    }
}
