package nulp.pist21.blackjack.model;

import java.util.List;

public class TableBox{

    private IPlayer player;
    private List<Card> hand;

    public void doStep() {

    }

    public boolean hasPlaces(){
        return player == null;
    }

    public void sitDown(IPlayer player) {
        if (!hasPlaces()) return;
        this.player = player;
    }

    public void standUp(IPlayer player) {
        if (player == this.player){
            this.player = null;
        }
    }

    public IPlayer[] getPlayers() {
        if (hasPlaces()) return new IPlayer[]{};
        return new IPlayer[]{ player };
    }

    public List<Card> getHand() {
        return hand;
    }
}
