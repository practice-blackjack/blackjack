package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.IHand;
import nulp.pist21.blackjack.model.IPlayer;

import java.util.ArrayList;
import java.util.List;

public class TableBox implements IHand {

    private IPlayer player;
    private List<Card> hand;

    private int bet;

    public TableBox() {
        hand = new ArrayList<>();
        bet = 0;
    }

    public boolean isFree(){
        return player == null;
    }

    public boolean sitDown(IPlayer player) {
        if (!isFree()) return false;
        this.player = player;
        return true;
    }

    public void makeFree() {
        this.player = null;
    }

    public boolean standUp(IPlayer player){
        if (player != this.player) return false;
        makeFree();
        return true;
    }

    public IPlayer getPlayer() {
        return player;
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
    public List<Card> getHand() {
        return hand;
    }

    public boolean isInGame(){
        return !isFree() && bet != 0;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet){
        if (isFree()) return;
        if (!player.takeMoney(bet)) makeFree();
        else this.bet = bet;
    }

}
