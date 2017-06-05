package nulp.pist21.blackjack.model.game;

import nulp.pist21.blackjack.model.IHand;
import nulp.pist21.blackjack.model.IStrategy;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Dealer implements IStrategy, IHand {

    private ArrayList<Card> hand;
    private Card hidenCard;

    public Dealer() {
        this.hand = new ArrayList<>();
        this.hidenCard = null;
    }

    @Override
    public GameAction getGameAction(GameWithDealer game){
        hand.set(0, hidenCard);
        hidenCard = null;
        return null;
    }

    @Override
    public List<Card> getHand() {
        return hand;
    }

    @Override
    public void giveCard(Card card) {
        if (hand.isEmpty()) {
            hidenCard = card;
            hand.add(Card.HIDEN_CARD);
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
