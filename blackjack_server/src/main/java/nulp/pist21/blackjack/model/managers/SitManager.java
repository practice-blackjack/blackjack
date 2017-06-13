package nulp.pist21.blackjack.model.managers;

import nulp.pist21.blackjack.model.Sit;

import java.util.Arrays;

public class SitManager {
    private Sit[] boxes;

    public SitManager(int boxes) {
        this.boxes = new Sit[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new Sit();
        }
    }

    public boolean isEmpty(){
        return !Arrays.stream(boxes).anyMatch(box -> box.isActivated());
    }

    public Sit[] getPlayingBoxes(){
        return Arrays.stream(boxes).filter(box -> box.isActivated()).toArray(Sit[]::new);
    }

    public Sit[] getBoxes() {
        return boxes;
    }
}
