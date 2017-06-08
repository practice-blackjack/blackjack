package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.User;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

@ClientEndpoint
public class InitEndpoint {

    private MessageFunction<StringMessage> registerFunction;
    private MessageFunction<TokenMessage> loginFunction;
    private MessageFunction<StringMessage> logoutFunction;

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        switch (JSON.parseObject(message, Message.class).getType()) {
            case "register":
                if (registerFunction != null) registerFunction.apply(JSON.parseObject(message, StringMessage.class));
                break;
            case "login":
                if (loginFunction != null) loginFunction.apply(JSON.parseObject(message, TokenMessage.class));
                break;
            case "logout":
                if (logoutFunction != null) logoutFunction.apply(JSON.parseObject(message, StringMessage.class));
                break;
        }
    }

    public void onRegisterListener(MessageFunction<StringMessage> function) {
        this.registerFunction = function;
    }

    public void onLoginListener(MessageFunction<TokenMessage> function) {
        this.loginFunction = function;
    }

    public void onLogoutListener(MessageFunction<StringMessage> function) {
        this.logoutFunction = function;
    }

    public void sendRegisterMessage(User user) {
        sendMessage(new UserMessage("register", user));
    }

    public void sendLoginMessage(User user) {
        sendMessage(new UserMessage("login", user));
    }

    public void sendLogoutMessage() {
        sendMessage(new Message("logout"));
    }

    private void sendMessage(Message message) {
        String json = JSON.toJSONString(message);
        session.getAsyncRemote().sendText(json);
    }

    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
