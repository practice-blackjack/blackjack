package nulp.pist21.blackjack.model;

public interface AbstractTable {
    String getName();
    int getRate();
    int getMaxPlayers();
    int getPlayersCount();

    void startRound();
}
