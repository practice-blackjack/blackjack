package nulp.pist21.blackjack.message;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.model.User;

public class UserMessage extends Message {

    private User user;

    public UserMessage() {
        this("", null);
    }

    public UserMessage(String type, User user) {
        super(type);
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}