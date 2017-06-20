package nulp.pist21.blackjack.model.deck;

public class Card {

    protected int suit;
    protected int value;

    public final static int ACE = 0;
    public final static int _2 = 1;
    public final static int _3 = 2;
    public final static int _4 = 3;
    public final static int _5 = 4;
    public final static int _6 = 5;
    public final static int _7 = 6;
    public final static int _8 = 7;
    public final static int _9 = 8;
    public final static int _10 = 9;
    public final static int JACK = 10;
    public final static int QUEEN = 11;
    public final static int KING = 12;
    public final static int UNDEFINED_VALUE = 13;
    public final static int MAX_VALUE = 13;


    public final static int SPADES = 0;
    public final static int HEARTS = 1;
    public final static int CLUBS = 2;
    public final static int DIAMONDS = 3;
    public final static int UNDEFINED_SUIT = 4;
    public final static int MAX_SUIT = 4;

    public final static String[] SUITS = new String[] {"spades", "hearts", "clubs", "diamonds", "*"};
    public final static String[] VALUES = new String[] {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "*"};

    public Card() {

    }

    public Card(int suit, int value) {
        this.suit = suit % MAX_SUIT;
        this.value = value % MAX_VALUE;
    }

    public Card(int card) {
        this.suit = Math.abs(card / MAX_VALUE % MAX_SUIT);
        this.value = Math.abs(card % MAX_VALUE);
    }

    public Card(long card) {
        int pow = (int) (Math.log(card)/Math.log(2));
        this.suit = Math.abs(pow / MAX_VALUE % MAX_SUIT);
        this.value = Math.abs(pow % MAX_VALUE);
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return VALUES[value] + " " + SUITS[suit];
    }
}
