package nulp.pist21.blackjack.server.data;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.model.User;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class TokenList {

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
