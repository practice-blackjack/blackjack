package nulp.pist21.blackjack.model;

public interface ITableBox{
    void doStep();
    boolean hasPlaces();
    void sitDown(IPlayer player);
    void standUp(IPlayer player);

    IPlayer[] getPlayers();
}
