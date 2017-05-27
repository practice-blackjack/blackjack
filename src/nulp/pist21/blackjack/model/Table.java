package nulp.pist21.blackjack.model;

public class Table {
    private String name;
    private int rate;
    private ITableBox[] boxes;
    private AbstractDeck deck;

    public Table(String name, int rate, ITableBox[] boxes, AbstractDeck deck) {
        this.name = name;
        this.rate = rate;
        this.boxes = boxes;
        this.deck = deck;
    }

    public String getName() {
        return name;
    }

    public int getRate() {
        return rate;
    }

    public ITableBox[] getBoxes() {
        return boxes;
    }

    public AbstractDeck getDeck() {
        return deck;
    }

}
