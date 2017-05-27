package nulp.pist21.blackjack.model;

import java.util.List;

public interface ITable {
    int getId();
    String getName();
    int getRate();
    TableBox[] getBoxes();

    List<IPlayer> getListeners();

    Player addUser(User user);
    void removePlayer(IPlayer player);
}
