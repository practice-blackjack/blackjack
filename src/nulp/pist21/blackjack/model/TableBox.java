package nulp.pist21.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class TableBox{

    private IPlayer player;
    private List<Card> hand;

    public TableBox() {
        hand = new ArrayList<>();
    }

    public void doStep() {

    }

    public boolean isFree(){
        return player == null;
    }

    public void sitDown(IPlayer player) {
        if (!isFree()) return;
        this.player = player;
    }

    public void standUp(IPlayer player) {
        if (player == this.player){
            this.player = null;
        }
    }

    public IPlayer getPlayer() {
        return player ;
    }

    public List<Card> getHand() {
        return hand;
    }
}
