package nulp.pist21.blackjack.server.data;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.actor.Actor;
import nulp.pist21.blackjack.server.actor.message.*;

import java.util.ArrayList;
import java.util.List;

public class UserManager extends AbstractActor {

    static public Props props() {
        return Props.create(UserManager.class, () -> new UserManager());
    }

    public UserManager() {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RegisterRequest.class, message -> {
                    User user = existUserName(message.user.getName());
                    boolean res = user == null;
                    if (res) {
                        addUser(message.user);
                    }
                    getSender().tell(new RegisterResponse(res), getSelf());
                })
                .match(LoginRequest.class, message -> {
                    User user = existUser(message.user);
                    if (user != null) {
                        Actor.tokenManager.tell(new RefreshUsersToken(user), getSender());
                    } else {
                        getSender().tell(new LoginResponse(-1), getSelf());
                    }
                })
                .match(MyDataNameRequest.class, message -> {
                    User user = existUserName(message.userName);
                    if (user == null) {
                        user = new User("", "");
                    }
                    getSender().tell(new MyDataResponse(user), getSelf());
                })
                .match(UserDataRequest.class, message -> {
                    User user = existUserName(message.userName);
                    if (user == null) {
                        user = new User("", "");
                    } else {
                        user.setPassword("");
                    }
                    getSender().tell(new UserDataResponse(user), getSelf());
                })
                .build();
    }




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
                return (User) u.clone();
            }
        }
        return null;
    }

    public String getUsers() {
        return userList.stream().map(u -> JSON.toJSONString(u)).reduce("", (a, b) -> a + "\n" + b);
    }

}
