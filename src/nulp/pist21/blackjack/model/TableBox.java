package nulp.pist21.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class TableBox{

    private IPlayer player;
    private List<Card> hand;

    public TableBox() {
        hand = new ArrayList<>();
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

    public void takeCard(IDeck deck){
        if (isFree()) return;
        hand.add(deck.next());
    }

    public List<Card> getHand() {
        return hand;
    }

}
