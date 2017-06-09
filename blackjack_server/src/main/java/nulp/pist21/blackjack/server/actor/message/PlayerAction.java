package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.TableInfo;

public class PlayerAction {

    public final TableInfo tableInfo;
    public final int place;
    public final String action;
    public final int bet;

    public PlayerAction(TableInfo tableInfo, int place, String action, int bet) {
        this.tableInfo = tableInfo;
        this.place = place;
        this.action = action;
        this.bet = bet;
    }

}
