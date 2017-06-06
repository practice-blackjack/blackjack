package nulp.pist21.blackjack.model.table.deck;

public class TurnableCard extends Card {
    Card hidden;

    public TurnableCard(int suit, int value) {
        super(suit, value);

        hidden = new Card(this.suit, this.value);

        this.suit = Card.UNDEFINED_SUIT;
        this.value = Card.UNDEFINED_VALUE;
    }

    public TurnableCard(int card) {
        super(card);

        hidden = new Card(this.suit, this.value);

        this.suit = Card.UNDEFINED_SUIT;
        this.value = Card.UNDEFINED_VALUE;
    }

    public TurnableCard(long card) {
        super(card);

        hidden = new Card(this.suit, this.value);

        this.suit = Card.UNDEFINED_SUIT;
        this.value = Card.UNDEFINED_VALUE;
    }

    public TurnableCard(Card card) {
        this(card.getSuit(), card.getValue());
    }

    public void open(){
        this.suit = hidden.getSuit();
        this.value = hidden.getValue();
    }

    public void close(){
        this.suit = Card.UNDEFINED_SUIT;
        this.value = Card.UNDEFINED_VALUE;
    }
}
