package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.game.IRound;
import nulp.pist21.blackjack.model.game.Round;

import java.util.Arrays;

public class Table {

    private TableBox[] boxes;
    private IDeck deck;

    public Table(int boxes, IDeck deck) {
        this.boxes = new TableBox[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new TableBox();
        }
        this.deck = deck;
    }

    public TableBox[] getBoxes() {
        return boxes;
    }

    public IRound startRound() {
        TableBox[] playingBoxes = Arrays.stream(boxes).filter(box -> box.isActivated()).toArray(TableBox[]::new);
        return new Round(playingBoxes, deck);
    }

    public IDeck getDeck() {
        return deck;
    }
}
