package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.User;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

import static nulp.pist21.blackjack.message.MessageConstant.TYPE_LOGIN;
import static nulp.pist21.blackjack.message.MessageConstant.TYPE_LOGOUT;
import static nulp.pist21.blackjack.message.MessageConstant.TYPE_REGISTER;

@ClientEndpoint
public class InitEndpoint {

    private MessageFunction<BooleanMessage> registerFunction;
    private MessageFunction<TokenMessage> loginFunction;
    private MessageFunction<BooleanMessage> logoutFunction;

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        switch (JSON.parseObject(message, Message.class).getType()) {
            case TYPE_REGISTER:
                if (registerFunction != null) registerFunction.apply(JSON.parseObject(message, BooleanMessage.class));
                break;
            case TYPE_LOGIN:
                if (loginFunction != null) loginFunction.apply(JSON.parseObject(message, TokenMessage.class));
                break;
            case TYPE_LOGOUT:
                if (logoutFunction != null) logoutFunction.apply(JSON.parseObject(message, BooleanMessage.class));
                break;
        }
    }

    public void onRegisterListener(MessageFunction<BooleanMessage> function) {
        this.registerFunction = function;
    }

    public void onLoginListener(MessageFunction<TokenMessage> function) {
        this.loginFunction = function;
    }

    public void onLogoutListener(MessageFunction<BooleanMessage> function) {
        this.logoutFunction = function;
    }

    public void sendRegisterMessage(User user) {
        sendMessage(new UserMessage(TYPE_REGISTER, user));
    }

    public void sendLoginMessage(User user) {
        sendMessage(new UserMessage(TYPE_LOGIN, user));
    }

    public void sendLogoutMessage() {
        sendMessage(new Message(TYPE_LOGOUT));
    }

    private void sendMessage(Message message) {
        try {
            if (session != null && session.isOpen()) {
                String json = JSON.toJSONString(message);
                session.getBasicRemote().sendText(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isOpen() {
        return session.isOpen();
    }

}
