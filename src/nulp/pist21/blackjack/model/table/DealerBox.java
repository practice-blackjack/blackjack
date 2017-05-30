package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.table.deck.Card;

import java.util.ArrayList;

public class DealerBox implements IBox {

    private ArrayList<Card> hand;
    private Card hiddenCard;

    public DealerBox() {
        this.hand = new ArrayList<>();
        this.hiddenCard = null;
    }

    @Override
    public Card[] getHand() {
        return hand.toArray(new Card[hand.size()]);
    }

    public void openCard(){
        hand.set(0, hiddenCard);
        hiddenCard = null;
    }

    @Override
    public void giveCard(Card card) {
        if (hand.size() == 0) {
            hiddenCard = card;
            hand.add(Card.HIDDEN_CARD);
        }
        else {
            hand.add(card);
        }
    }

    @Override
    public void takeCards() {
        hand.clear();
    }
}
