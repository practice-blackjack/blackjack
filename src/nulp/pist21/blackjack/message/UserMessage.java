package nulp.pist21.blackjack.message;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.model.User;

public class UserMessage extends StringMessage {

    private User user;

    public UserMessage() {
        super("");
        user = new User("", "");
    }

    public UserMessage(String message, User user) {
        super(message);
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}