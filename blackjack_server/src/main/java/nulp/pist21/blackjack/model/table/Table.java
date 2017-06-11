package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.deck.IDeck;

import java.util.Arrays;

public class Table {
    private TableBox[] boxes;
    private IDeck deck;

    private int minBet;
    private int maxBet;

    public Table(int boxes, int minBet, int maxBet, IDeck deck) {
        this.boxes = new TableBox[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new TableBox();
        }
        this.minBet = minBet;
        this.maxBet = maxBet;
        this.deck = deck;
    }

    public boolean isEmpty(){
        return !Arrays.stream(boxes).anyMatch(box -> box.isActivated());
    }

    public TableBox[] getBoxes() {
        return boxes;
    }

    public IDeck getDeck() {
        return deck;
    }

    public int getMinBet() {
        return minBet;
    }

    public int getMaxBet() {
        return maxBet;
    }
}
