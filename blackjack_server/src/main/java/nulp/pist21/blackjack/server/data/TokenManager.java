package nulp.pist21.blackjack.server.data;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.actor.Actor;
import nulp.pist21.blackjack.server.actor.message.*;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class TokenManager extends AbstractActor {

    static public Props props() {
        return Props.create(TokenManager.class, () -> new TokenManager());
    }

    public TokenManager() {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MyDataTokenRequest.class, message -> {
                    User user = getUser(message.token);
                    Actor.userManager.tell(new MyDataNameRequest(user.getName()), getSender());
                })
                .match(LogoutTokenRequest.class, message -> {
                    deleteUser(message.token);
                    getSender().tell(new LogoutResponse(true), getSelf());
                })
                .match(RefreshUsersToken.class, message -> {
                    deleteUser(message.user);
                    long token = addUser(message.user);
                    getSender().tell(new LoginResponse(token), getSelf());
                })
                .match(TokenCheck.class, message -> {
                    User user = getUser(message.token);
                    getSender().tell(new TokenChecked(user != null), getSelf());
                })
                .match(SitTableTokenRequest.class, message -> {
                    User user = getUser(message.token);
                    if (user != null) {
                        Actor.tableManager.tell(new SitTableUserRequest(message.tableInfo, message.place, user), getSender());
                    } else {
                        getSender().tell(new SitTableResponse(false), getSelf());
                    }
                })
                .build();
    }


    private Map<Long, User> tokenList = new TreeMap<>();

    public long addUser(User user) {
        long token = -1;
        while (token == -1) {
            token = new Random().nextLong();
        }
        tokenList.put(token, user);
        return token;
    }

    public User getUser(long token) {
        return tokenList.get(token);
    }

    public long getToken(User user) {
        for (Map.Entry<Long, User> e : tokenList.entrySet()) {
            User value = e.getValue();
            if (user.getName().equals(value.getName()) && user.getPassword().equals(value.getPassword())) {
                return e.getKey();
            }
        }
        return -1;
    }

    public void deleteUser(long token) {
        tokenList.remove(token);
    }

    public void deleteUser(User user) {
        tokenList.remove(getToken(user));
    }

    public String getTokens() {
        return tokenList.entrySet().stream().map(e -> e.getKey() + " " + JSON.toJSONString(e.getValue())).reduce("", (a, b) -> a + "\n" + b);
    }

}
