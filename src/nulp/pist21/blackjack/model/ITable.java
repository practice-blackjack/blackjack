package nulp.pist21.blackjack.model;

import java.util.List;

public interface ITable {
    String getName();
    int getRate();
    ITableBox[] getBoxes();

    List<IPlayer> getListeners();

    void addUser(User user);
}
