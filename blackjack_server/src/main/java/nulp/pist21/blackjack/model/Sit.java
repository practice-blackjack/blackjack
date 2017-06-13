package nulp.pist21.blackjack.model;

public class Sit {

    private boolean occupied;
    public Sit() {
        occupied = false;
    }

    public boolean isActivated(){
        return occupied;
    }

    public boolean activate(){
        if (occupied){
            return false;
        }
        occupied = true;
        return true;
    }

    public boolean deactivate(){
        if (occupied == false){
            return false;
        }
        occupied = false;
        return true;
    }
}
