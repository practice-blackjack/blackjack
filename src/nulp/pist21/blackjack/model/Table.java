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
    public Player addUser(User user) {
        Player player = new Player(user);
        listeners.add(player);
        return player;
    }

    @Override
    public void removePlayer(IPlayer player) {
        listeners.remove(player);
        for (ITableBox box : boxes) {
            box.standUp(player);
        }
    }

    public IDeck getDeck() {
        return deck;
    }

}
