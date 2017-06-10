package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private int cash;
    private Card[] hand;

    public Player() {
        this("", 0, new Card[0]);
    }

    public Player(String name, int cash, Card[] hand) {
        this.name = name;
        this.cash = cash;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

}
