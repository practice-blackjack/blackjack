package nulp.pist21.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class Table implements ITable {
    private int id;
    private String name;
    private int rate;
    private TableBox[] boxes;
    private TableBox dealerBox;
    private IDeck deck;

    private List<IPlayer> listeners;

    public Table(int id, String name, int rate, int boxes, IDeck deck) {
        this.name = name;
        this.rate = rate;
        this.boxes = new TableBox[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new TableBox();
        }
        this.dealerBox = new TableBox();
        this.deck = deck;

        listeners = new ArrayList<>();
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
    public TableBox[] getBoxes() { return boxes; }

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
        for (TableBox box : boxes) {
            box.standUp(player);
        }
    }

    public IDeck getDeck() {
        return deck;
    }

    public void giveFirstCards(){
        for (TableBox box: boxes){
            if (box.isFree()) continue;

            for (int i = 0; i < 2; i++) {
                box.getHand().add(deck.next());
            }
        }

        for (int i = 0; i < 2; i++) {
            dealerBox.getHand().add(deck.next());
        }
    }

    public void takeCards(){
        for (TableBox box: boxes){
            box.getHand().clear();
        }
    }

    @Override
    public int getId() {
        return id;
    }
}
