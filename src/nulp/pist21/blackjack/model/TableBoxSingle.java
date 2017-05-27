package nulp.pist21.blackjack.model;

import java.util.List;

public class TableBoxSingle implements ITableBox {

    private Player player;
    private List<Card> hand;

    public void doStep() {

    }

    public boolean canSit() {
        return false;
    }

    @Override
    public int getPoints() {
        return 0;
    }
}
