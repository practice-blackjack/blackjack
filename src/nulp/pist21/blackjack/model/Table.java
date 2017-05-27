package nulp.pist21.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class Table implements ITable {
    private String name;
    private int rate;
    private ITableBox[] boxes;
    private IDeck deck;

    private List<IPlayer> listeners;

    public Table(String name, int rate, ITableBox[] boxes, IDeck deck) {
        this.name = name;
        this.rate = rate;
        this.boxes = boxes;
        this.deck = deck;

        listeners = new ArrayList<IPlayer>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getRate() {
        return rate;
    }

    @Override
    public ITableBox[] getBoxes() { return boxes; }

    @Override
    public List<IPlayer> getListeners() {
        return listeners;
    }

    @Override
    public void addUser(User user) {
        IPlayer player = new Player(user);
        listeners.add(player);
    }

    public IDeck getDeck() {
        return deck;
    }

}
