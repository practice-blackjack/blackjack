package nulp.pist21.blackjack.model.game;

import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.TurnableCard;
import nulp.pist21.blackjack.model.game.calculating.Combination;
import nulp.pist21.blackjack.model.game.managers.PlayManager;

import java.util.ArrayList;
import java.util.List;

public class Dealer implements IHand {
    private List<Card> hand;
    private TurnableCard hiddenCard;

    public Dealer() {
        hand = new ArrayList<>();
    }

    @Override
    public void giveCard(Card card){
        if (hand.size() == 0){
            hiddenCard = new TurnableCard(card);
            hand.add(hiddenCard);
        }
        else {
            hand.add(card);
        }

    }

    @Override
    public void takeCards(){
        hand.clear();
    }

    @Override
    public Card[] getHand(){
        return hand.toArray(new Card[hand.size()]);
    }

    public PlayManager.Actions doStep(IHand[] players) {
        hiddenCard.open();
        if (new Combination(this).getPoints() <= 16){
            return PlayManager.Actions.HIT;
        }
        return PlayManager.Actions.STAND;
    }
}
