package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.Actions.GameAction;
import nulp.pist21.blackjack.model.Deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Dealer implements IStrategy, IHand {

    private ArrayList<Card> hand;

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
        hand.add(card);
    }

    @Override
    public void takeCards() {
        hand.clear();
    }
}
