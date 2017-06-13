package nulp.pist21.blackjack.model.table;

import java.util.Arrays;

public class Table {
    private TableBox[] boxes;

    public Table(int boxes) {
        this.boxes = new TableBox[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new TableBox();
        }
    }

    public boolean isEmpty(){
        return !Arrays.stream(boxes).anyMatch(box -> box.isActivated());
    }

    public TableBox[] getPlayingBoxes(){
        return Arrays.stream(boxes).filter(box -> box.isActivated()).toArray(TableBox[]::new);
    }

    public TableBox[] getBoxes() {
        return boxes;
    }
}
