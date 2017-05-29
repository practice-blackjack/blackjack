package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Dealer implements IStrategy, IHand {

    private ArrayList<Card> hand;
    private Card hidenCard;

    public Dealer() {
        this.hand = new ArrayList<>();
    }

    @Override
    public GameAction getGameAction(GameWithDealer game){
        return null;
    }

    @Override
    public List<Card> getHand() {
        return hand;
    }

    @Override
    public void giveCard(Card card) {
        if (hand.isEmpty()) hand.add(Card.HIDEN_CARD);
        else hand.remove(Card.HIDEN_CARD);
        hand.add(card);
    }

    @Override
    public void takeCards() {
        hand.clear();
    }
}
