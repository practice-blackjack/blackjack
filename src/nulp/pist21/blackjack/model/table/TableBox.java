package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.table.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class TableBox{

    private List<Card> hand;

    public TableBox() {
        hand = new ArrayList<>();
    }

    public void giveCard(Card card){
        hand.add(card);
    }

    public void takeCards(){
        hand.clear();
    }

    public Card getCard(int index) {
        if (index >= 0 && index < hand.size()){
            return hand.get(index);
        }
        return null;
    }

    public int getCardsCount() {
        return hand.size();
    }
}
