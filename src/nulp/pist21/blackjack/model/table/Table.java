package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.table.game.GameWithDealer;

public class Table {
    private int rate;
    private TableBox[] boxes;

    private GameWithDealer game;

    public Table(int rate, int boxes, GameWithDealer game) {
        this.rate = rate;
        this.boxes = new TableBox[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new TableBox();
        }
        this.game = game;
    }

    public int getRate() {
        return rate;
    }

    public TableBox[] getBoxes() { return boxes; }

    public GameWithDealer getGame() {
        return game;
    }
}
