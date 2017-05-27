package nulp.pist21.blackjack.model;

public class Table implements ITable {
    private String name;
    private int rate;
    private ITableBox[] boxes;
    private IDeck deck;

    public Table(String name, int rate, ITableBox[] boxes, IDeck deck) {
        this.name = name;
        this.rate = rate;
        this.boxes = boxes;
        this.deck = deck;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getRate() {
        return rate;
    }

    public ITableBox[] getBoxes() { return boxes; }

    public IDeck getDeck() {
        return deck;
    }

}
