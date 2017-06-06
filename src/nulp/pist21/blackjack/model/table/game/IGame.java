package nulp.pist21.blackjack.model.table.game;

import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.TableBox;

public interface IGame {
    void start(TableBox[] playingBoxes);
    boolean next(GameAction action);
    void end();
    int getCurrentIndex();
    TableBox getBox(int index);
    int getBoxCount();
}
