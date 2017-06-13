package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.Player;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.game.IBank;
import nulp.pist21.blackjack.model.game.IHand;

import java.util.ArrayList;
import java.util.List;

public class TableBox implements IActivating, IHand, IBank {

    private Player player;
    private List<Card> hand;
    private int bet;

    public TableBox() {
        hand = new ArrayList<>();
        player = null;
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

    @Override
    public boolean isActivated(){
        return player != null;
    }

    public boolean activate(Player player){
        if (this.player != null){
            return false;
        }
        this.player = player;
        return true;
    }

    public boolean deactivate(Player player){
        if (this.player != player){
            return false;
        }
        this.player = null;
        hand.clear();
        return true;
    }

    @Override
    public int getBet() {
        return bet;
    }

    @Override
    public void setBet(int bet) {
        this.bet = bet;
    }

    public Player getPlayer() {
        return player;
    }
}
