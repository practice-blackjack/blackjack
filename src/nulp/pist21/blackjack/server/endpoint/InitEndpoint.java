package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.data.ProgramData;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/init")
public class InitEndpoint {

    private MessageFunction<UserMessage> registerFunction;
    private MessageFunction<UserMessage> loginFunction;
    private MessageFunction<Message> unloginFunction;

    private Session session;
    private final ProgramData programData = ProgramData.get();
    private long token = -1;

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("init open");
        this.session = session;
        programData.initManager.add(this);
    }

    @OnClose
    public void onClose() {
        System.out.println("init close");
        programData.initManager.remove(this);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("init message " + message);
        switch (JSON.parseObject(message, Message.class).getType()) {
            case "register":
                if (registerFunction != null) registerFunction.apply(JSON.parseObject(message, UserMessage.class));
                break;
            case "login":
                if (loginFunction != null) loginFunction.apply(JSON.parseObject(message, UserMessage.class));
                break;
            case "unlogin":
                if (unloginFunction != null) unloginFunction.apply(JSON.parseObject(message, Message.class));
                break;
        }
    }

    public void onRegisterListener(MessageFunction<UserMessage> function) {
        this.registerFunction = function;
    }

    public void onLoginListener(MessageFunction<UserMessage> function) {
        this.loginFunction = function;
    }

    public void onUnloginListener(MessageFunction<Message> function) {
        this.unloginFunction = function;
    }

    public void sendRegisterMessage(String message) {
        sendMessage(new StringMessage("register", message));
    }

    public void sendLoginMessage(long token) {
        sendMessage(new TokenMessage("login", token));
    }

    public void sendUnloginMessage() {
        sendMessage(new StringMessage("unlogin", "+"));
    }

    private void sendMessage(Message message) {
        String json = JSON.toJSONString(message);
        System.out.println("init send " + json);
        session.getAsyncRemote().sendText(json);
    }

}
