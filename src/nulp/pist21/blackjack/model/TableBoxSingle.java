package nulp.pist21.blackjack.model;

import java.util.List;

public class TableBoxSingle implements ITableBox {

    private Player player;
    private List<Card> hand;

    @Override
    public void doStep() {

    }

    @Override
    public boolean hasPlaces(){
        return player != null;
    }
}
