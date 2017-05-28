package nulp.pist21.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class TableBox{

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

    public void sitDown(IPlayer player) {
        if (!isFree()) return;
        this.player = player;
    }

    public void makeFree() {
        this.player = null;
    }

    public IPlayer getPlayer() {
        return player;
    }

    public void giveCard(Card card){
        hand.add(card);
    }

    public void takeCards(){
        hand.clear();
    }


    public boolean isInGame(){
        return !isFree() && bet != 0;
    }

    public List<Card> getHand() {
        return hand;
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
