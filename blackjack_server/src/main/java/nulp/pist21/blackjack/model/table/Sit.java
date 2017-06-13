package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.Player;

public class Sit {

    private Player player;
    public Sit() {
        player = null;
    }

    public boolean isActivated(){
        return player != null;
    }

    public boolean activate(Player player){
        if (this.player != null){
            return false;
        }
        this.player = player;
        return true;
    }

    public boolean deactivate(Player player){
        if (this.player != player){
            return false;
        }
        this.player = null;
        return true;
    }

    public Player getPlayer() {
        return player;
    }
}
