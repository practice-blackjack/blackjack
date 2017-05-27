package nulp.pist21.blackjack.model;

import java.util.List;

public class TableBoxSingle implements ITableBox {

    private IPlayer player;
    private List<Card> hand;

    @Override
    public void doStep() {

    }

    @Override
    public boolean hasPlaces(){
        return player == null;
    }

    @Override
    public void sit(IPlayer player) {
        if (!hasPlaces()) return;
        this.player = player;
    }

    @Override
    public IPlayer[] getPlayers() {
        if (hasPlaces()) return new IPlayer[]{};
        return new IPlayer[]{ player };
    }
}
