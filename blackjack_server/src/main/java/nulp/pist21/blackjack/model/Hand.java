package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private Player player;
    private List<Card> hand;
    private int bet;

    public Hand() {
        hand = new ArrayList<>();
        player = null;
    }

    public void giveCard(Card card){
        hand.add(card);
    }

    public void takeCards(){
        hand.clear();
    }

    public Card[] getHand(){
        return hand.toArray(new Card[hand.size()]);
    }
}
