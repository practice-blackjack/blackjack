package nulp.pist21.blackjack.model.table.game;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.deck.Card;
import nulp.pist21.blackjack.model.table.deck.TurnableCard;

import java.util.ArrayList;
import java.util.List;

public class Dealer implements IHand, IStrategy {
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

    @Override
    public GameAction doStep(IGame game, int index) {
        hiddenCard.open();
        if (GameWithDealer.Combination.getPoints(this) <= 16){
            return new GameAction(GameAction.Actions.HIT);
        }
        return new GameAction(GameAction.Actions.STAND);
    }
}
