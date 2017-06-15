package nulp.pist21.blackjack.model.deck;

public class TurnableCard extends Card {
    Card hidden;

    public TurnableCard(int suit, int value) {
        hidden = new Card(suit, value);

        this.suit = Card.UNDEFINED_SUIT;
        this.value = Card.UNDEFINED_VALUE;
    }

    public TurnableCard(int card) {
        hidden = new Card(card);

        this.suit = Card.UNDEFINED_SUIT;
        this.value = Card.UNDEFINED_VALUE;
    }

    public TurnableCard(long card) {
        hidden = new Card(card);

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
