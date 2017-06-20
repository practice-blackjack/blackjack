package nulp.pist21.blackjack.model;

import nulp.pist21.blackjack.model.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class TableFullInfo {

    private Card[] dealerHand;
    private Player[] players;
    private int currentUser;

    public TableFullInfo() {
        this(new Card[0], new Player[0], 0);
    }

    public TableFullInfo(Card[] dealerHand, Player[] players, int currentUser) {
        this.dealerHand = dealerHand;
        this.players = players;
        this.currentUser = currentUser;
    }

    public Card[] getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(Card[] dealerHand) {
        this.dealerHand = dealerHand;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }

}
