package nulp.pist21.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int id;
    private String name;
    private int rate;
    private TableBox[] boxes;
    private TableBox dealerBox;
    private IDeck deck;

    private List<ISpectator> spectators;

    public Table(int id, String name, int rate, int boxes, IDeck deck) {
        this.name = name;
        this.rate = rate;
        this.boxes = new TableBox[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new TableBox();
        }
        this.dealerBox = new TableBox();
        this.deck = deck;

        spectators = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getRate() {
        return rate;
    }

    public TableBox[] getBoxes() { return boxes; }

    public List<ISpectator> getSpectators() {
        return spectators;
    }

    public void addSpectator(User player) {
        spectators.add(player);
    }

    public void removePlayer(IPlayer player) {
        spectators.remove(player);
        for (TableBox box : boxes) {
            if (box.getPlayer() == player)
            box.makeFree();
        }
    }

    public IDeck getDeck() {
        return deck;
    }

    public void giveFirstCards(){
        for (TableBox box: boxes){
            if (!box.isInGame()) continue;
            for (int i = 0; i < 2; i++) {
                box.giveCard(deck.next());
            }
        }

        for (int i = 0; i < 2; i++) {
            dealerBox.giveCard(deck.next());
        }
    }

    public void takeCards(){
        for (TableBox box: boxes){
            box.takeCards();
        }
    }

    public int getId() {
        return id;
    }
}
