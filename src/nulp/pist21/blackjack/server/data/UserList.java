package nulp.pist21.blackjack.server.data;

import nulp.pist21.blackjack.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    private List<User> userList = new ArrayList<>();

    public boolean addUser(User user) {
        user.setCash(1000);
        return userList.add(user);
    }

    public boolean existUser(User user) {
        for (User u : userList) {
            if (user.getName().equals(u.getName()) && user.getPassword().equals(u.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public boolean existUserName(String name) {
        for (User u : userList) {
            if (name.equals(u.getName())) {
                return true;
            }
        }
        return false;
    }

}
