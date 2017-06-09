package nulp.pist21.blackjack.server.endpoint;

import akka.actor.ActorRef;
import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.server.actor.Actor;
import nulp.pist21.blackjack.server.actor.InitActor;
import nulp.pist21.blackjack.server.actor.message.LoginRequest;
import nulp.pist21.blackjack.server.actor.message.LogoutRequest;
import nulp.pist21.blackjack.server.actor.message.RegisterRequest;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/init")
public class InitEndpoint {

    private Session session;
    private ActorRef actor;
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
        actor = Actor.system.actorOf(InitActor.props(this));
    }

    @OnClose
    public void onClose() {
        System.out.println("init close");
        Actor.system.stop(actor);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("init message " + message);
        switch (JSON.parseObject(message, Message.class).getType()) {
            case "register": {
                UserMessage msg = JSON.parseObject(message, UserMessage.class);
                RegisterRequest registerRequest = new RegisterRequest(msg.getUser());
                actor.tell(registerRequest, ActorRef.noSender());
            }break;
            case "login": {
                UserMessage msg = JSON.parseObject(message, UserMessage.class);
                LoginRequest loginRequest = new LoginRequest(msg.getUser());
                actor.tell(loginRequest, ActorRef.noSender());
            }break;
            case "logout": {
                Message msg = JSON.parseObject(message, Message.class);
                LogoutRequest logoutRequest = new LogoutRequest();
                actor.tell(logoutRequest, ActorRef.noSender());
            }break;
        }
    }

    public void sendRegisterMessage(boolean isOk) {
        sendMessage(new StringMessage("register", isOk ? "user added" : "error"));
    }

    public void sendLoginMessage(long token) {
        sendMessage(new TokenMessage("login", token));
    }

    public void sendLogoutMessage() {
        sendMessage(new StringMessage("logout", "logout ok"));
    }

    private void sendMessage(Message message) {
        String json = JSON.toJSONString(message);
        System.out.println("init send " + json);
        session.getAsyncRemote().sendText(json);
    }

}
