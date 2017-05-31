package nulp.pist21.blackjack.model.table.game;

import javafx.util.Pair;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.TableBox;

import java.util.List;

public interface IGame {
    void start(TableBox[] playingBoxes);
    boolean next(GameAction action);
    List<Pair<TableBox, Float>> end();
    int getValue(int index);
    int getCurrentIndex();
    TableBox getBox(int index);
    int getBoxCount();
}
