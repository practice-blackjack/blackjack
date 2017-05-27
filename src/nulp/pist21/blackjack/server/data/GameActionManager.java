package nulp.pist21.blackjack.server.data;

import nulp.pist21.blackjack.message.GameActionMessage;
import nulp.pist21.blackjack.message.WaitMessage;
import nulp.pist21.blackjack.model.Table;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.endpoint.GameActionEndpoint;

import java.util.Map;
import java.util.TreeMap;

public class GameActionManager {

    private TokenList list;
    private Map<Long, GameActionEndpoint> gameActionEndpoints = new TreeMap<>();

    public GameActionManager(TokenList list) {
        this.list = list;
    }

    public void add(long token, GameActionEndpoint endpoint) {
        gameActionEndpoints.put(token, endpoint);
        endpoint.onMessageListener((GameActionMessage gameActionMessage) -> {
        });
    }

    public void delete(long token) {
        gameActionEndpoints.remove(token);
    }

    public void waitAction(Table table, User user) {
        long token = list.getToken(user);
        GameActionEndpoint endpoint = gameActionEndpoints.get(token);
        WaitMessage waitMessage = new WaitMessage("wait action", table);
        endpoint.sendMessage(waitMessage);
    }

}
