package nulp.pist21.blackjack.model;

public interface ITableBox {
    void doStep();
    boolean hasPlaces();
    void sit(Player player);
}
