package nulp.pist21.blackjack.message;

import nulp.pist21.blackjack.model.Card;

public class CardMessage extends StringMessage {

    private Card[] cards;
    private boolean[] visible;
    private int me;

    public CardMessage() {
        super("");
        cards = new Card[0];
        visible = new boolean[0];
        me = -1;
    }

    public CardMessage(String message, Card[] cards, boolean[] visible, int me) {
        super(message);
        this.cards = cards;
        this.visible = visible;
        this.me = me;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public boolean[] getVisible() {
        return visible;
    }

    public void setVisible(boolean[] visible) {
        this.visible = visible;
    }

    public int getMe() {
        return me;
    }

    public void setMe(int me) {
        this.me = me;
    }

}
