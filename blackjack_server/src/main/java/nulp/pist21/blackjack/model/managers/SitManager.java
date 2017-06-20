package nulp.pist21.blackjack.model.managers;

import nulp.pist21.blackjack.model.Sit;
import nulp.pist21.blackjack.model.User;

import java.util.ArrayList;
import java.util.Arrays;

public class SitManager implements Cloneable {
    private Sit[] sits;

    public SitManager(int sits) {
        this.sits = new Sit[sits];
        for (int i = 0; i < sits; i++) {
            this.sits[i] = new Sit();
        }
    }

    public boolean isEmpty(){
        return !Arrays.stream(sits).anyMatch(box -> box.isActivated());
    }

    public Sit[] getPlayingSits(){
        return Arrays.stream(sits).filter(box -> box.isActivated()).toArray(Sit[]::new);
    }

    public int[] getPlayingSitIndexes(){
        ArrayList<Integer> playingSits = new ArrayList<>();
        for (int i = 0; i < sits.length; i++){
            if (sits[i].isActivated()){
                playingSits.add(i);
            }
        }
        return playingSits.stream().mapToInt(Integer::intValue).toArray();
    }


    public Sit[] getSits() {
        return sits;
    }

    public void leave(User user) {
        for (Sit sit: sits) {
            sit.stand(user);
        }
    }

    @Override
    public Object clone() {
        SitManager clone = new SitManager(this.sits.length);
        for (int i = 0; i < this.sits.length; i++){
            if (this.sits[i].isActivated()){
                clone.sits[i].sit(this.sits[i].getUser());
            }
        }
        return clone;
    }
}
