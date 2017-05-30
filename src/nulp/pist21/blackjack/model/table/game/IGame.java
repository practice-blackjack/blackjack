package nulp.pist21.blackjack.model.table.game;

import javafx.util.Pair;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.table.IBox;

import java.util.List;

public interface IGame {
    void start(IBox[] playingBoxes);
    boolean next(GameAction action);
    List<Pair<IBox, Float>> end();
    int getValue(int index);
    int getCurrentIndex();
    IBox[] getPlayingBoxes();
}
