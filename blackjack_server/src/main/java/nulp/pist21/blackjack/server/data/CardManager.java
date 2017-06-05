package nulp.pist21.blackjack.server.data;

import nulp.pist21.blackjack.message.CardMessage;
import nulp.pist21.blackjack.model.Card;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.endpoint.CardEndpoint;

import java.util.Map;
import java.util.TreeMap;

public class CardManager {

    private TokenList list;
    private Map<Long, CardEndpoint> cardEndpoints = new TreeMap<>();

    public CardManager(TokenList list) {
        this.list = list;
    }

    public void add(long token, CardEndpoint endpoint) {
        cardEndpoints.put(token, endpoint);
    }

    public void delete(long token) {
        cardEndpoints.remove(token);
    }

    public void pushCards(User user, int userPlace, Card[] cards, boolean[] visible) {
        long token = list.getToken(user);
        CardEndpoint endpoint = cardEndpoints.get(token);
        CardMessage cardMessage = new CardMessage("push cards", cards, visible, userPlace);
        endpoint.sendMessage(cardMessage);
    }

}