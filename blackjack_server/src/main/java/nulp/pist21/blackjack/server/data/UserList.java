package nulp.pist21.blackjack.server.data;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    private List<User> userList = new ArrayList<>();

    public boolean addUser(User user) {
        user.setCash(1000);
        return userList.add(user);
    }

    public User existUser(User user) {
        for (User u : userList) {
            if (user.getName().equals(u.getName()) && user.getPassword().equals(u.getPassword())) {
                return u;
            }
        }
        return null;
    }

    public User existUserName(String name) {
        for (User u : userList) {
            if (name.equals(u.getName())) {
                return u;
            }
        }
        return null;
    }

    public String getUsers() {
        return userList.stream().map(u -> JSON.toJSONString(u)).reduce("", (a, b) -> a + "\n" + b);
    }

}
