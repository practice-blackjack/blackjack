package nulp.pist21.blackjack.model;

public class Card {

    private int suit;
    private int value;

    public final static String[] SUITS = new String[] {"spades", "hearts", "clubs", "diamonds"};
    public final static String[] VALUES = new String[] {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public Card(int suit, int value) {
        this.suit = suit % 4;
        this.value = value % 13;
    }

    public Card(int card) {
        this.suit = Math.abs(card / 13 % 4);
        this.value = Math.abs(card % 13);
    }

    public Card(long card) {
        int pow = (int) (Math.log(card)/Math.log(2));
        this.suit = Math.abs(pow / 13 % 4);
        this.value = Math.abs(pow % 13);
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return VALUES[value] + " " + SUITS[suit];
    }

}
